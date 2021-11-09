package restaurant.com.deliverydriversqueuemanager.service;

import org.springframework.data.repository.query.Param;
import restaurant.com.deliverydriversqueuemanager.model.Driver;

import java.util.List;

public interface DriverService {
    Driver save(Driver driver);
    List<Driver> findAll();
    Driver findDriverByUsername(@Param("username") String username);
    void deleteAll();
}
