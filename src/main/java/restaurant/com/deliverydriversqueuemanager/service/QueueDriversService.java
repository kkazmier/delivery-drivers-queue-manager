package restaurant.com.deliverydriversqueuemanager.service;

import restaurant.com.deliverydriversqueuemanager.model.Driver;

import java.util.List;

public interface QueueDriversService {
    List<Driver> gelAllDrivers();
    List<Driver> gelLoggedDrivers();
    List<Driver> getReadyDrivers();
}
