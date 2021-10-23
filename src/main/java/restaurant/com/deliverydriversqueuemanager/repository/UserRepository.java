package restaurant.com.deliverydriversqueuemanager.repository;

import org.springframework.data.repository.CrudRepository;
import restaurant.com.deliverydriversqueuemanager.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAll();
}
