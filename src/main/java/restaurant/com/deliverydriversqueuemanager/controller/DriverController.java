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
import restaurant.com.deliverydriversqueuemanager.util.Time;

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
    public String beginWork() {
        username = getUsernameCurrentLoggedUser();
        logger.info("Begin work: " + username);
        user = userService.findByUsername(username);
        logger.info("User: " + user);
        logger.info(user.toString());
        driver = user.getDriver();
        driver.setDriverStatus(DriverStatus.READY);
        driver.setChangeDriverStatusTime(LocalDateTime.now());
        driver.setChangeDriverStatusTimeStr(Time.getCurrentTime());
        driverService.save(driver);
        return "redirect:/pizzaDriversQueue";
    }

    @RequestMapping("/beginDelivery")
    public String beginDelivery() {
        username = getUsernameCurrentLoggedUser();
        user = userService.findByUsername(username);
        driver = user.getDriver();
        driver.setDriverStatus(DriverStatus.DELIVERING);
        driver.setChangeDriverStatusTime(LocalDateTime.now());
        driver.setChangeDriverStatusTimeStr(Time.getCurrentTime());
        driverService.save(driver);
        return "redirect:/pizzaDriversQueue";
    }

    @RequestMapping("/endingDelivery")
    public String endingDelivery() {
        username = getUsernameCurrentLoggedUser();
        user = userService.findByUsername(username);
        driver = user.getDriver();
        driver.setDriverStatus(DriverStatus.BACK);
        driver.setChangeDriverStatusTime(LocalDateTime.now());
        driver.setChangeDriverStatusTimeStr(Time.getCurrentTime());
        driverService.save(driver);
        return "redirect:/pizzaDriversQueue";
    }

    @RequestMapping("/beginBreak")
    public String beginBreak() {
        username = getUsernameCurrentLoggedUser();
        user = userService.findByUsername(username);
        driver = user.getDriver();
        driver.setDriverStatus(DriverStatus.BREAK);
        driver.setChangeDriverStatusTime(LocalDateTime.now());
        driver.setChangeDriverStatusTimeStr(Time.getCurrentTime());
        driverService.save(driver);
        return "redirect:/pizzaDriversQueue";
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
