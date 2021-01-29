package az.guavapay.controller;


import az.guavapay.mapper.OrderMapper;
import az.guavapay.model.order.*;
import az.guavapay.model.user.User;
import az.guavapay.model.enums.OrderStatus;
import az.guavapay.security.JwtTokenUtil;
import az.guavapay.service.order.OrderService;
import az.guavapay.service.user.UserService;
import az.guavapay.util.AccountNumberUtil;
import az.guavapay.util.CardNumberUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@RestController
@RequestMapping("${cm.root.order}")
@Api("Endpoint for order creation")
public class OrderController {

    private final OrderService orderService;
    private final String jwtHeader;
    private final JwtTokenUtil jwtTokenUtil;
    private final UserService userService;

    public OrderController(@Value("${jwt.header}") String jwtHeader,
                           OrderService orderService,
                           JwtTokenUtil jwtTokenUtil,
                           UserService userService) {
        this.jwtHeader = jwtHeader;
        this.orderService = orderService;
        this.jwtTokenUtil = jwtTokenUtil;
        this.userService = userService;
    }

    @PostMapping("/create")
    @ApiOperation("create order by authentication token")
    public ResponseEntity<?> createOder(@RequestBody OrderRequest orderRequest, HttpServletRequest request) {
        User verifiedUser = userService.findUserByUserName(getUserNameFromHttpServletRequest(request));
        Order order = OrderMapper.INSTANCE.mapToEntity(orderRequest, verifiedUser);
        return ResponseEntity.ok(orderService.saveOrder(order));
    }

    @GetMapping("/get-orders")
    @ApiOperation("get all orders by authentication token")
    public List<OrderDTO> orderList(HttpServletRequest request) {
        User verifiedUser = userService.findUserByUserName(getUserNameFromHttpServletRequest(request));
        List<Order> orderList = orderService.getOrderListByUser(verifiedUser);
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Order order : orderList
        ) {
            orderDTOList.add(OrderMapper.INSTANCE.mapResponse(order));
        }
        return orderDTOList;
    }

    @GetMapping("/find-order/{orderId}")
    @ApiOperation("get order by order id and authentication token")
    public Order getOrder(@PathVariable("orderId") Long orderId, HttpServletRequest request) {
        User verifiedUser = userService.findUserByUserName(getUserNameFromHttpServletRequest(request));
        List<Order> orderList = orderService.findOrdersByUser(verifiedUser);
        if (!orderList.isEmpty()) {
            Order order = orderList.stream().filter(
                    orderFromDb -> orderFromDb.getId().equals(orderId)).findAny().get();
            return order;
        }
        return null;
    }

    @PutMapping("/update-order")
    @ApiOperation("update order by order info and authentication token")
    public ResponseEntity<?> updateOrder(@RequestBody OrderUpdateRequest updateRequest, HttpServletRequest request) {
        User verifiedUser = userService.findUserByUserName(getUserNameFromHttpServletRequest(request));
        Order order = OrderMapper.INSTANCE.mapToEntity(updateRequest, verifiedUser);
        order.setId(updateRequest.getId());
        Optional<Order> existsOrder = orderService.findById(order.getId());
        existsOrder.ifPresent(orderOptional -> {
            if (!orderOptional.getStatus().equals(OrderStatus.CONFIRMED)) {
                order.setRegDate(orderOptional.getRegDate());
                orderService.saveOrder(order);
            }
        });
        return ResponseEntity.status(HttpStatus.OK).body("order successfully updated");

    }

    @DeleteMapping("/delete-order/{orderId}")
    @ApiOperation("delete order by orderId and authentication token")
    public ResponseEntity<?> deleteOrder(@PathVariable(value = "orderId") Long orderId, HttpServletRequest request) {
        User verifiedUser = userService.findUserByUserName(getUserNameFromHttpServletRequest(request));
        List<Order> orders = orderService.findOrdersByUser(verifiedUser);
        try {
            Order order = orders.stream().filter(order1 -> order1.getId().equals(orderId)).findAny().get();
            if (!order.getStatus().equals(OrderStatus.CONFIRMED)) {
                orderService.deleteOrderByOrderId(orderId);
                return ResponseEntity.ok("order deleted");
            }
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order already confirmed,you cannot delete it!");
        } catch (Exception e) {
            throw new IllegalArgumentException("order was not deleted");
        }
    }

    @PostMapping("/submit-order/{orderId}")
    @ApiOperation("submit order by authentication token")
    public ResponseEntity<Object> confirmOrder(@PathVariable(value = "orderId") Long orderId, HttpServletRequest request) {
        User user = userService.findUserByUserName(getUserNameFromHttpServletRequest(request));
        List<Order> orderList = orderService.findOrdersByUser(user);
        Order order = orderList.stream().filter(
                orderFromDb -> orderFromDb.getId().equals(orderId)).findAny().get();
        if (!order.getStatus().equals(OrderStatus.CONFIRMED)) {
            order.setStatus(OrderStatus.CONFIRMED);
            if (Objects.nonNull(orderService.saveOrder(order))) {
                OrderConfirmationResponse orderConfirmationResponse = OrderConfirmationResponse.builder()
                        .accountNumber(new AccountNumberUtil().generateAccountNumber())
                        .cardNumber(new CardNumberUtil().generateCardNumber())
                        .httpStatus(HttpStatus.OK)
                        .build();
                return ResponseEntity.ok(orderConfirmationResponse);
            } else {
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("The order was not confirmed");
            }
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Order already confirmed");
        }
    }


    private String getUserNameFromHttpServletRequest(HttpServletRequest request) {
        final String requestHeader = request.getHeader(this.jwtHeader);
        if (Objects.nonNull(requestHeader)
                && requestHeader.startsWith("Bearer ")
                && !requestHeader.contains("null")) {
            String authorizationToken = requestHeader.substring(7);
            return jwtTokenUtil.getUserNameFromToken(authorizationToken);
        } else {
            return null;
        }
    }
}
