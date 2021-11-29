package restaurant.com.deliverydriversqueuemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import restaurant.com.deliverydriversqueuemanager.model.Driver;
import restaurant.com.deliverydriversqueuemanager.model.DriverStatus;
import restaurant.com.deliverydriversqueuemanager.model.User;
import restaurant.com.deliverydriversqueuemanager.model.Workplace;
import restaurant.com.deliverydriversqueuemanager.service.DriverServiceImpl;
import restaurant.com.deliverydriversqueuemanager.service.SecurityService;
import restaurant.com.deliverydriversqueuemanager.service.UserService;
import restaurant.com.deliverydriversqueuemanager.util.ActiveUserStore;
import restaurant.com.deliverydriversqueuemanager.util.UserValidator;

import javax.servlet.http.HttpSessionBindingListener;
import java.time.LocalDateTime;
import java.util.List;

@Controller
public class UserController implements HttpSessionBindingListener {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private final DriverServiceImpl driverService;
    private SecurityService securityService;
    private UserValidator userValidator;
    private ActiveUserStore activeUserStore;

    @Autowired
    public UserController(UserService userService, DriverServiceImpl driverService, SecurityService securityService, UserValidator userValidator, ActiveUserStore activeUserStore) {
        this.userService = userService;
        this.driverService = driverService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.activeUserStore = activeUserStore;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        User user = new User();
        model.addAttribute("userForm", user);
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        if (userForm.getDriver() == null) {
            Driver driver = new Driver();
            driver.setDriverStatus(DriverStatus.UNDEFINED);
            driver.setChangeDriverStatusTime(LocalDateTime.now());
            driver.setChangeDriverStatusTimeStr(LocalDateTime.now().toString());
            userForm.setDriver(driverService.save(driver));
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/home";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/welcome";
        }
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }

//    @RequestMapping(value = "/login", method = RequestMethod.POST)
//    public String processLoginForm(HttpSession session, @ModelAttribute("user") User user,
//                                   BindingResult result, Model model, final RedirectAttributes redirectAttributes) {
//        //what goes here?
//        return "redirect:/welcome";
//    }

    @GetMapping({"/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }

//    @GetMapping("/logout")
//    public String logout(Model model) {
//        return "logout";
//    }
//
//    @PostMapping("/logout")
//    public String logout() {
//        return "logout";
//    }

    @GetMapping({"/", "/home"})
    public String home(Authentication authentication) {
        return "home";
    }

    @GetMapping("/loggedUsers")
    @ResponseBody
    public List<String> getLoggedUsers(Model model) {
        return activeUserStore.getUsers();
    }

    @GetMapping("/loggedUser")
    @ResponseBody
    public String getLoggedUser(Model model, Authentication authentication) {
        return authentication.getName();
    }

    @GetMapping("/allUsers")
    @ResponseBody
    public List<User> allUsers(Model model) {
        return userService.getAllUsers();
    }

    @GetMapping("/deleteAllUsers")
    @ResponseBody
    public void deleteAllUsers() {
        userService.deleteAll();
    }

    @GetMapping("/setWorkplace/{workplace}")
    String setWorkPlace(@PathVariable String workplace, Model model) {
        String username = securityService.getLoggedUsername();
        userService.setWorkplace(username, workplace);
        model.addAttribute("workplace", workplace);
        return "workplace";
    }
}
