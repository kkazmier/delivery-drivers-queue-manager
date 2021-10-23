package restaurant.com.deliverydriversqueuemanager.service;

import restaurant.com.deliverydriversqueuemanager.model.User;

import java.util.List;

public interface UserService {
    void create(User user);
    void save(User user);
    User findByUsername(String username);
    List<User> getAllUsers();
}
