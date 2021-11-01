package restaurant.com.deliverydriversqueuemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import restaurant.com.deliverydriversqueuemanager.model.Driver;
import restaurant.com.deliverydriversqueuemanager.model.DriverStatus;
import restaurant.com.deliverydriversqueuemanager.model.User;
import restaurant.com.deliverydriversqueuemanager.service.DriverServiceImpl;
import restaurant.com.deliverydriversqueuemanager.service.UserServiceImpl;

import java.time.LocalDateTime;

@Controller
@RequestMapping("driver")
public class DriverController {
    private final Logger logger = LoggerFactory.getLogger(DriverController.class);
    private final UserServiceImpl userService;
    private final DriverServiceImpl driverService;
    private String username;
    private User user;
    private Driver driver;

    @Autowired
    public DriverController(UserServiceImpl userService, DriverServiceImpl driverService) {
        this.userService = userService;
        this.driverService = driverService;
    }

    @RequestMapping("/beginWork")
    @ResponseBody
    public String beginWork() {
        username = getUsernameCurrentLoggedUser();
        user = userService.findByUsername(username);
        logger.info(user.toString());
        driver = user.getDriver();
        driver.setDriverStatus(DriverStatus.READY);
        driver.setChangeDriverStatusTime(LocalDateTime.now());
        driverService.save(driver);
        return "Begin work: " + username + driver.getChangeDriverStatusTime() + " " + driver.getDriverStatus();
    }

    @RequestMapping("/beginDelivery")
    @ResponseBody
    public String beginDelivery() {
        username = getUsernameCurrentLoggedUser();
        user = userService.findByUsername(username);
        driver = user.getDriver();
        driver.setDriverStatus(DriverStatus.DELIVERING);
        driver.setChangeDriverStatusTime(LocalDateTime.now());
        driverService.save(driver);
        return "Begin delivery: " + username + driver.getChangeDriverStatusTime() + " " + driver.getDriverStatus();
    }

    @RequestMapping("/endingDelivery")
    @ResponseBody
    public String endingDelivery() {
        username = getUsernameCurrentLoggedUser();
        user = userService.findByUsername(username);
        driver = user.getDriver();
        driver.setDriverStatus(DriverStatus.BACK);
        driver.setChangeDriverStatusTime(LocalDateTime.now());
        driverService.save(driver);
        return "Ending delivery: " + username + driver.getChangeDriverStatusTime() + " " + driver.getDriverStatus();
    }

    @RequestMapping("/beginBreak")
    @ResponseBody
    public String beginBreak() {
        username = getUsernameCurrentLoggedUser();
        user = userService.findByUsername(username);
        driver = user.getDriver();
        driver.setDriverStatus(DriverStatus.BREAK);
        driver.setChangeDriverStatusTime(LocalDateTime.now());
        driverService.save(driver);
        return "Begin break: " + username + driver.getChangeDriverStatusTime() + " " + driver.getDriverStatus();
    }

    public String getUsernameCurrentLoggedUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (!(authentication instanceof AnonymousAuthenticationToken)) {
            String currentUserName = authentication.getName();
            return currentUserName;
        } else {
            return "Name not found";
        }
    }
}
