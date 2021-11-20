package restaurant.com.deliverydriversqueuemanager.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.com.deliverydriversqueuemanager.model.Driver;
import restaurant.com.deliverydriversqueuemanager.repository.DriverRepository;

@Service
public class DriverServiceImpl implements DriverService {
    private final DriverRepository driverRepository;

    @Autowired
    public DriverServiceImpl(DriverRepository driverRepository) {
        this.driverRepository = driverRepository;
    }

    @Override
    public Driver save(Driver driver) {
        return driverRepository.save(driver);
    }

    @Override
    public void setWorkplace(String workplace) {

    }
}
