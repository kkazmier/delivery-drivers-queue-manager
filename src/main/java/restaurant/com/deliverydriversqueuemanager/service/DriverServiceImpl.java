package restaurant.com.deliverydriversqueuemanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.com.deliverydriversqueuemanager.model.Driver;
import restaurant.com.deliverydriversqueuemanager.repository.DriverRepository;

import java.util.List;

@Service
public class DriverServiceImpl implements DriverService {
    private static final Logger logger = LoggerFactory.getLogger(DriverServiceImpl.class);
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
    public void deleteAll() {
        driverRepository.deleteAll();
    }

    @Override
    public List<Driver> findAll() {
        return driverRepository.findAll();
    }

    @Override
    public Driver findDriverByUsername(String username) {
        return driverRepository.findDriverByUsername(username);
    }
}
