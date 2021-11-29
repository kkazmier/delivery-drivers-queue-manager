package restaurant.com.deliverydriversqueuemanager.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;
import restaurant.com.deliverydriversqueuemanager.model.User;

import java.util.List;

public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
    List<User> findAll();
    void deleteAll();

    @Modifying
    @Transactional
    @Query("update User u set u.workPlace = :workplace where u.username = :username")
    void updateWorkplace(String username, String workplace);
}
