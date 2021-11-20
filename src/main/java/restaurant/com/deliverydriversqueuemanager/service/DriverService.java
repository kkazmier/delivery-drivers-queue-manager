package restaurant.com.deliverydriversqueuemanager.service;

import restaurant.com.deliverydriversqueuemanager.model.Driver;

public interface DriverService {
    Driver save(Driver driver);
    void setWorkplace(String workplace);
}
