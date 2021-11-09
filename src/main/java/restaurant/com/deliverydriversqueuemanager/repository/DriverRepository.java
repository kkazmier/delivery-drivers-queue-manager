package restaurant.com.deliverydriversqueuemanager.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import restaurant.com.deliverydriversqueuemanager.model.Driver;

import java.util.List;

//@Repository
public interface DriverRepository extends CrudRepository<Driver, Long> {
    Driver save(Driver driver);
    List<Driver> findAll();
    @Query(value = "select * from drivers join users on drivers.id=users.driver_id where users.username = :username", nativeQuery = true)
    //@Query(value = "select * from drivers where drivers.id=22", nativeQuery = true)
    Driver findDriverByUsername(@Param("username") String username);
    void deleteAll();
}
