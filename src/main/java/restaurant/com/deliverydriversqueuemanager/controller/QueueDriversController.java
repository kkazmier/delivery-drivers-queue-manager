package restaurant.com.deliverydriversqueuemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import restaurant.com.deliverydriversqueuemanager.model.Driver;
import restaurant.com.deliverydriversqueuemanager.model.DriverStatus;
import restaurant.com.deliverydriversqueuemanager.model.User;
import restaurant.com.deliverydriversqueuemanager.model.Workplace;
import restaurant.com.deliverydriversqueuemanager.service.QueueDriversServiceImpl;
import restaurant.com.deliverydriversqueuemanager.service.SecurityService;
import restaurant.com.deliverydriversqueuemanager.service.UserServiceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Controller("queue")
public class QueueDriversController {
    private final Logger logger = LoggerFactory.getLogger(QueueDriversController.class);
    private QueueDriversServiceImpl queueDriversService;
    private SecurityService securityService;

    @Autowired
    public QueueDriversController(QueueDriversServiceImpl queueDriversService) {
        this.queueDriversService = queueDriversService;
    }

    @GetMapping("/readyDrivers")
    String getReadyDrivers(Model model) {
        model.addAttribute("readyDrivers", queueDriversService.getReadyDrivers());
        return "readyDrivers";
    }

    @GetMapping("/allDrivers")
    @ResponseBody
    List<Driver> getAllDrivers() {
        return queueDriversService.gelAllDrivers();
    }

    @GetMapping("/loggedDrivers")
    String getLoggedDrivers(Model model) {
        List<Driver> loggedDrivers = queueDriversService.gelLoggedDrivers();
        model.addAttribute("loggedDrivers", loggedDrivers);
        return "loggedDrivers";
    }

    @GetMapping("/driversQueue")
    String driversQueue() {
        return "driversQueue";
    }

    @GetMapping("/pizzaDriversQueue")
    String pizzaDriversQueue(Model model) {
        List<Driver> loggedDrivers = queueDriversService.gelLoggedDrivers();
        List<Driver> pizzaDrivers = loggedDrivers.stream()
                .filter(driver -> driver.getUser().getWorkPlace().equals(Workplace.PIZZA))
                .collect(Collectors.toList());
        Comparator<Driver> cmp = Comparator.comparing(Driver::getChangeDriverStatusTime);
        pizzaDrivers.sort(cmp);
        List<Driver> readyDrivers = new ArrayList<>();
        List<Driver> deliveringDrivers = new ArrayList<>();
        List<Driver> backDrivers = new ArrayList<>();
        List<Driver> breakDrivers = new ArrayList<>();
        for (Driver driver: pizzaDrivers) {
            String status = driver.getDriverStatus();
            if (status != null) {
                switch (status) {
                    case DriverStatus
                            .READY: readyDrivers.add(driver); break;
                    case DriverStatus
                            .BACK: backDrivers.add(driver); break;
                    case DriverStatus
                            .DELIVERING: deliveringDrivers.add(driver); break;
                    case DriverStatus
                            .BREAK: breakDrivers.add(driver); break;
                }
            }
        }
        model.addAttribute("loggedDrivers", loggedDrivers);
        model.addAttribute("pizzaDrivers", pizzaDrivers);
        model.addAttribute("pizzaReadyDrivers", readyDrivers);
        model.addAttribute("pizzaBackDrivers", backDrivers);
        model.addAttribute("pizzaDeliveringDrivers", deliveringDrivers);
        model.addAttribute("pizzaBreakDrivers", breakDrivers);
        logger.info(pizzaDrivers.toString());

        return "pizzaDriversQueue";
    }

    @GetMapping("/setStatusToReady")
    String setWorkPlace() {
        String username = securityService.getLoggedUsername();
        return "workplace";
    }

    @GetMapping("/pizzaDriversQueuePresentation")
    String pizzaDriversQueuePresentation(Model model) {
        List<Driver> driversForPresentation = queueDriversService.getDriversForPresentation();


        driversForPresentation.stream()
                .filter(driver -> driver.getUser().getWorkPlace().equals(Workplace.PIZZA))
                .collect(Collectors.toList());
        Comparator<Driver> cmp = Comparator.comparing(Driver::getChangeDriverStatusTime);
        List<Driver> readyDriversPresentation = new ArrayList<>();
        List<Driver> deliveringDriversPresentation = new ArrayList<>();
        List<Driver> backDriversPresentation = new ArrayList<>();
        List<Driver> breakDriversPresentation = new ArrayList<>();
        for (Driver driver: driversForPresentation) {
            String status = driver.getDriverStatus();
            if (status != null) {
                switch (status) {
                    case DriverStatus
                            .READY: readyDriversPresentation.add(driver); break;
                    case DriverStatus
                            .BACK: backDriversPresentation.add(driver); break;
                    case DriverStatus
                            .DELIVERING: deliveringDriversPresentation.add(driver); break;
                    case DriverStatus
                            .BREAK: breakDriversPresentation.add(driver); break;
                }
            }
        }
        model.addAttribute("pizzaReadyDriversPresentation", readyDriversPresentation);
        model.addAttribute("pizzaBackDriversPresentation", backDriversPresentation);
        model.addAttribute("pizzaDeliveringDriversPresentation", deliveringDriversPresentation);
        model.addAttribute("pizzaBreakDriversPresentation", breakDriversPresentation);


        return "pizzaDriversQueuePresentation";
    }
}
