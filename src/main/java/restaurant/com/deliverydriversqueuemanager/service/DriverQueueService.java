package restaurant.com.deliverydriversqueuemanager.service;

import restaurant.com.deliverydriversqueuemanager.model.Driver;

import java.util.List;

public interface DriverQueueService {
    List<Driver> allLoggedDrivers();
}
