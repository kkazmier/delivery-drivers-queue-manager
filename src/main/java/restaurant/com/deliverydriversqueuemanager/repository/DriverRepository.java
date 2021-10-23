package restaurant.com.deliverydriversqueuemanager.repository;

import org.springframework.data.repository.CrudRepository;
import restaurant.com.deliverydriversqueuemanager.model.Driver;

public interface DriverRepository extends CrudRepository<Driver, Long> {
    Driver save(Driver driver);
}
