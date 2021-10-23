package restaurant.com.deliverydriversqueuemanager.Controller;

import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import restaurant.com.deliverydriversqueuemanager.model.User;
import restaurant.com.deliverydriversqueuemanager.service.SecurityService;
import restaurant.com.deliverydriversqueuemanager.service.UserService;
import restaurant.com.deliverydriversqueuemanager.util.UserValidator;

@Controller
@AllArgsConstructor
public class UserController {
    private static final Logger logger = LoggerFactory.getLogger(UserController.class);
    private final UserService userService;
    private SecurityService securityService;
    private UserValidator userValidator;

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
    public String login(Model model, String error, String logout) {
        if (securityService.isAuthenticated()) {
            return "redirect:/";
        }
        if (error != null) {
            model.addAttribute("error", "Your username and password is invalid.");
        }
        if (logout != null) {
            model.addAttribute("message", "You have been logged out successfully.");
        }
        return "login";
    }

    @GetMapping({ "/welcome"})
    public String welcome(Model model) {
        return "welcome";
    }

    @GetMapping("/loggedUsers")
    public String getLoggedUsers(Model model) {
        //model.addAttribute("loggedUsers", userService.getAllActiveUsers());
        return "loggedUsers";
    }
}
