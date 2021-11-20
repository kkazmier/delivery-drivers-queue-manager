package restaurant.com.deliverydriversqueuemanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.com.deliverydriversqueuemanager.model.Driver;
import restaurant.com.deliverydriversqueuemanager.repository.DriverRepository;
import restaurant.com.deliverydriversqueuemanager.util.ActiveUserStore;

import java.util.ArrayList;
import java.util.List;

@Service
public class DriverQueueServiceImpl implements DriverQueueService {
    private final Logger logger = LoggerFactory.getLogger(DriverQueueServiceImpl.class);
    private DriverRepository driverRepository;
    private ActiveUserStore activeUserStore;

    @Autowired
    public DriverQueueServiceImpl(DriverRepository driverRepository, ActiveUserStore activeUserStore) {
        this.driverRepository = driverRepository;
        this.activeUserStore = activeUserStore;
    }

    @Override
    public List<Driver> allLoggedDrivers() {
        List<Driver> loggedDrivers = new ArrayList<>();
        List<String> loggedUsers = activeUserStore.users;

        return loggedDrivers;
    }
}
