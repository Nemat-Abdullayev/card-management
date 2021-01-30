package az.guavapay.service.user;

import az.guavapay.model.user.User;
import az.guavapay.repository.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional
    public User createUser(User user) {
        try {
            user.setPassword(user.getPassword());
            return userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }

    }

    @Transactional
    public void updateUser(User user) {
        try {
            userRepository.save(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public User findUserByUserName(String username) {
        return userRepository.findByUsername(username);
    }

}
