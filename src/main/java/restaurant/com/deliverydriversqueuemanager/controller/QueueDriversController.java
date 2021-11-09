package restaurant.com.deliverydriversqueuemanager.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import restaurant.com.deliverydriversqueuemanager.model.Driver;
import restaurant.com.deliverydriversqueuemanager.model.DriverStatus;
import restaurant.com.deliverydriversqueuemanager.service.QueueDriversServiceImpl;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

@Controller
public class QueueDriversController {
    private QueueDriversServiceImpl queueDriversService;

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
        Comparator<Driver> cmp = Comparator.comparing(Driver::getChangeDriverStatusTime);
        loggedDrivers.sort(cmp);
        List<Driver> readyDrivers = new ArrayList<>();
        List<Driver> deliveringDrivers = new ArrayList<>();
        List<Driver> backDrivers = new ArrayList<>();
        List<Driver> breakDrivers = new ArrayList<>();
        for (Driver driver: loggedDrivers) {
            String status = driver.getDriverStatus();
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
        model.addAttribute("readyDrivers", readyDrivers);
        model.addAttribute("backDrivers", backDrivers);
        model.addAttribute("deliveringDrivers", deliveringDrivers);
        model.addAttribute("breakDrivers", breakDrivers);
        return "loggedDrivers";
    }
}
