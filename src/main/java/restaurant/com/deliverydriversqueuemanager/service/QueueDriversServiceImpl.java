package restaurant.com.deliverydriversqueuemanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import restaurant.com.deliverydriversqueuemanager.model.Driver;
import restaurant.com.deliverydriversqueuemanager.model.DriverStatus;
import restaurant.com.deliverydriversqueuemanager.model.User;
import restaurant.com.deliverydriversqueuemanager.repository.DriverRepository;
import restaurant.com.deliverydriversqueuemanager.util.ActiveUserStore;
import restaurant.com.deliverydriversqueuemanager.util.Comparators;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class QueueDriversServiceImpl implements QueueDriversService {
    Logger logger = LoggerFactory.getLogger(QueueDriversServiceImpl.class);
    private final DriverRepository driverRepository;
    private final ActiveUserStore userStore;

    @Autowired
    public QueueDriversServiceImpl(DriverRepository driverRepository, ActiveUserStore userStore) {
        this.driverRepository = driverRepository;
        this.userStore = userStore;
    }

    @Override
    public List<Driver> gelAllDrivers() {
        return driverRepository.findAll();
    }

    @Override
    public List<Driver> gelLoggedDrivers() {
        List<Driver> drivers = new ArrayList<>();
        driverRepository.findAll().stream().forEach(driver -> {
            if (userStore.users.contains(driver.getUser().getUsername())) {
                drivers.add(driver);
            }
        });
        return drivers;
    }

    @Override
    public List<Driver> getReadyDrivers() {
        Comparator<Driver> cmp = Comparator.comparing(Driver::getChangeDriverStatusTime);
        List<Driver> readyDrivers = driverRepository.findAll();
        if (readyDrivers == null) {
            readyDrivers = new ArrayList<>();
        }
        logger.info(String.valueOf(readyDrivers.toString()));
        readyDrivers = readyDrivers.stream()
                .filter(driver -> driver.getDriverStatus().equals(DriverStatus.READY))
                .sorted(cmp)
                .collect(Collectors.toList());
        for (Driver driver: readyDrivers) {
            logger.info(driver.getUser().getUsername());
        }
        return readyDrivers;
    }


}
