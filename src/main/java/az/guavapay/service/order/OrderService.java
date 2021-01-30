package az.guavapay.service.order;

import az.guavapay.model.order.Order;
import az.guavapay.model.user.User;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

public interface OrderService {

    Order saveOrder(Order order);

    List<Order> getOrderListByUser(User user);

    List<Order> findOrdersByUser(User user);

    void deleteOrderByOrderId(Long orderId) throws Exception;

    Optional<Order> findById(Long id);

}
