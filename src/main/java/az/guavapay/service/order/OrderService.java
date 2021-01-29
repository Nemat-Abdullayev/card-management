package az.guavapay.service.order;

import az.guavapay.model.user.User;
import az.guavapay.model.order.Order;
import az.guavapay.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;

    public Order saveOrder(Order order) {
        return orderRepository.save(order);
    }

    public List<Order> getOrderListByUser(User user) {
        return orderRepository.findAllByUser(user);
    }

    public Optional<Order> findOrderByOrderId(Long orderId) {
        try {
            return orderRepository.findById(orderId);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

    public List<Order> findOrdersByUser(User user) {
        try {
            return orderRepository.findByUser(user);
        } catch (Exception e) {
            return null;
        }
    }

    public void deleteOrderByOrderId(Long orderId) throws Exception {
        orderRepository.deleteById(orderId);
    }

    public boolean isExistsUser(User user) {
        try {
            return Objects.nonNull(orderRepository.findByUser(user));
        } catch (Exception e) {
            return false;
        }
    }

    public Optional<Order> findById(Long id) {
        try {
            return orderRepository.findById(id);
        } catch (Exception e) {
            return Optional.empty();
        }
    }

}
