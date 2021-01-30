package az.guavapay.service.order;

import az.guavapay.model.user.User;
import az.guavapay.model.order.Order;
import az.guavapay.repository.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Transactional
    public Order saveOrder(Order order) {
        try {
            return orderRepository.save(order);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
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

    @Transactional
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
