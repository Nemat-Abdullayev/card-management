package az.guavapay.service.user;

import az.guavapay.model.user.User;
import org.springframework.stereotype.Service;

public interface UserService {

    User createUser(User user);

    void updateUser(User user);

    User findUserByUserName(String username);
}
