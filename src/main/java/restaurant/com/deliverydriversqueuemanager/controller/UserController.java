package restaurant.com.deliverydriversqueuemanager.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import restaurant.com.deliverydriversqueuemanager.model.User;
import restaurant.com.deliverydriversqueuemanager.service.SecurityService;
import restaurant.com.deliverydriversqueuemanager.service.UserService;
import restaurant.com.deliverydriversqueuemanager.util.ActiveUserStore;
import restaurant.com.deliverydriversqueuemanager.util.UserValidator;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.List;

@Controller
public class UserController implements HttpSessionBindingListener {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private SecurityService securityService;
    private UserValidator userValidator;
    private ActiveUserStore activeUserStore;

    @Autowired
    public UserController(UserService userService, SecurityService securityService, UserValidator userValidator, ActiveUserStore activeUserStore) {
        this.userService = userService;
        this.securityService = securityService;
        this.userValidator = userValidator;
        this.activeUserStore = activeUserStore;
    }

    @GetMapping("/registration")
    public String registration(Model model) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        model.addAttribute("userForm", new User());
        return "registration";
    }

    @PostMapping("/registration")
    public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
        userValidator.validate(userForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        }
        userService.save(userForm);
        securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
        return "redirect:/welcome";
    }

    @GetMapping("/login")
    public String login(Model model, String error, String logout, Authentication authentication) {
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

    @GetMapping({"/", "/welcome"})
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

    @GetMapping("/home")
    public String home(Authentication authentication) {
        return "home";
    }

    @GetMapping("/loggedUsers")
    @ResponseBody
    public List<String> getLoggedUsers(Model model) {
        return activeUserStore.getUsers();
    }

    @GetMapping("/allUsers")
    @ResponseBody
    public List<User> allUsers(Model model) {
        return userService.getAllUsers();
    }
}