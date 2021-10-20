package restaurant.com.deliverydriversqueuemanager.repository;

import org.springframework.data.repository.CrudRepository;
import restaurant.com.deliverydriversqueuemanager.model.User;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
