package restaurant.com.deliverydriversqueuemanager.service;

import org.springframework.ui.Model;
import restaurant.com.deliverydriversqueuemanager.model.Driver;
import restaurant.com.deliverydriversqueuemanager.model.User;

import java.util.List;

public interface QueueDriversService {
    List<Driver> gelAllDrivers();
    List<Driver> gelLoggedDrivers();
    List<Driver> getReadyDrivers();
    List<Driver> getDriversForPresentation();
}
