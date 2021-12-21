package restaurant.com.deliverydriversqueuemanager.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;
import restaurant.com.deliverydriversqueuemanager.model.Driver;
import restaurant.com.deliverydriversqueuemanager.model.DriverStatus;
import restaurant.com.deliverydriversqueuemanager.model.User;
import restaurant.com.deliverydriversqueuemanager.model.Workplace;
import restaurant.com.deliverydriversqueuemanager.repository.DriverRepository;
import restaurant.com.deliverydriversqueuemanager.util.ActiveUserStore;
import restaurant.com.deliverydriversqueuemanager.util.Comparators;
import restaurant.com.deliverydriversqueuemanager.util.Time;

import java.time.LocalDateTime;
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
            logger.info("driver: " + driver.toString());
            logger.info("users from userstore: " + userStore.users.toString());
            if (userStore.users.contains(driver.getUser().getUsername())) {
                drivers.add(driver);
            }
        });
        return drivers;
    }

    @Override
    public List<Driver> getReadyDrivers() {
        Comparator<Driver> cmp = Comparator.comparing(Driver::getChangeDriverStatusTimeStr);
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

    @Override
    public List<Driver> getDriversForPresentation() {
        Comparator<Driver> cmp = Comparator.comparing(Driver::getChangeDriverStatusTimeStr);
        LocalDateTime time = LocalDateTime.now();
        List<User> users = new ArrayList<>();
        List<Driver> drivers = new ArrayList<>();

        User user = new User("ddab", "Dobromir", "Dąb", Workplace.PIZZA);
        Driver driver = new Driver();
        driver.setDriverStatus(DriverStatus.READY);
        driver.setChangeDriverStatusTimeStr(Time.getStringTime(time));
        driver.setUser(user);
        user.setDriver(driver);
        users.add(user);
        drivers.add(driver);

        user = new User("ppszenica", "Przemysław", "Pszenica", Workplace.PIZZA);
        driver = new Driver();
        driver.setDriverStatus(DriverStatus.BACK);
        driver.setChangeDriverStatusTimeStr(Time.getStringTime(time.plusMinutes(13)));
        driver.setUser(user);
        user.setDriver(driver);
        users.add(user);
        drivers.add(driver);

        user = new User("jjarm", "Jaromir", "Jarmuż", Workplace.PIZZA);
        driver = new Driver();
        driver.setDriverStatus(DriverStatus.DELIVERING);
        driver.setChangeDriverStatusTimeStr(Time.getStringTime(time.plusMinutes(15)));
        driver.setUser(user);
        user.setDriver(driver);
        users.add(user);
        drivers.add(driver);

        user = new User("aaron", "Artur", "Aronia", Workplace.PIZZA);
        driver = new Driver();
        driver.setDriverStatus(DriverStatus.BREAK);
        driver.setChangeDriverStatusTimeStr(Time.getStringTime(time.plusMinutes(17)));
        driver.setUser(user);
        user.setDriver(driver);
        users.add(user);
        drivers.add(driver);

        user = new User("bbakl", "Bronisław", "Bakłażan", Workplace.PIZZA);
        driver = new Driver();
        driver.setDriverStatus(DriverStatus.READY);
        driver.setChangeDriverStatusTimeStr(Time.getStringTime(time.plusMinutes(18)));
        driver.setUser(user);
        user.setDriver(driver);
        users.add(user);
        drivers.add(driver);

user = new User("ccebul", "Cecylia", "Cebula", Workplace.PIZZA);
        driver = new Driver();
        driver.setDriverStatus(DriverStatus.READY);
        driver.setChangeDriverStatusTimeStr(Time.getStringTime(time.plusMinutes(21)));
        driver.setUser(user);
        user.setDriver(driver);
        users.add(user);
        drivers.add(driver);

user = new User("zziemn", "Zdzisław", "Ziemniak", Workplace.PIZZA);
        driver = new Driver();
        driver.setDriverStatus(DriverStatus.READY);
        driver.setChangeDriverStatusTimeStr(Time.getStringTime(time.plusMinutes(18)));
        driver.setUser(user);
        user.setDriver(driver);
        users.add(user);
        drivers.add(driver);

user = new User("mmarch", "Marcin", "Marchewka", Workplace.PIZZA);
        driver = new Driver();
        driver.setDriverStatus(DriverStatus.READY);
        driver.setChangeDriverStatusTimeStr(Time.getStringTime(time.plusMinutes(25)));
        driver.setUser(user);
        user.setDriver(driver);
        users.add(user);
        drivers.add(driver);

        drivers.sort(cmp);
        logger.info(drivers.toString());
        return drivers;
    }
}
