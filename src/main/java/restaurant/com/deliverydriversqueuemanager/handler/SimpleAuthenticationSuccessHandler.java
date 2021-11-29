package restaurant.com.deliverydriversqueuemanager.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import restaurant.com.deliverydriversqueuemanager.model.Driver;
import restaurant.com.deliverydriversqueuemanager.model.DriverStatus;
import restaurant.com.deliverydriversqueuemanager.model.User;
import restaurant.com.deliverydriversqueuemanager.model.Workplace;
import restaurant.com.deliverydriversqueuemanager.service.UserServiceImpl;
import restaurant.com.deliverydriversqueuemanager.util.ActiveUserStore;
import restaurant.com.deliverydriversqueuemanager.util.LoggedUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDateTime;

@Component("simpleAuthenticationSuccessHandler")
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(SimpleAuthenticationSuccessHandler.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    ActiveUserStore activeUserStore;
    private UserServiceImpl userService;

    @Autowired
    public SimpleAuthenticationSuccessHandler(ActiveUserStore activeUserStore, @Lazy UserServiceImpl userService) {
        logger.info("Login handle initiated.");
        this.userService = userService;
        this.activeUserStore = activeUserStore;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        logger.info("onAuthenticationSuccess()");
        HttpSession session = request.getSession(false);
        if (session != null) {
            String username = authentication.getName();
            resetUserValuesAfterLogin(username);
            LoggedUser user = new LoggedUser(authentication.getName(), activeUserStore);
            session.setAttribute("user", user);
            logger.info(username + " logged.");
        }
        handle(request, response, authentication);
        clearAuthenticationAttributes(request);
    }

    private void handle(HttpServletRequest request,
                        HttpServletResponse response,
                        Authentication authentication) throws IOException {
        String targetUrl = determineTargetUrl(authentication);
        if (response.isCommitted()) {
            logger.debug(
                    "Response has already been committed. Unable to redirect to "
                            + targetUrl);
            return;
        }

//        User user = userService.findUserByEmail(authentication.getName());
//
//        globalSettings.setCustomer(user.getCustomer());
//        globalSettings.getAllValues();

        redirectStrategy.sendRedirect(request, response, targetUrl);
    }

    private void clearAuthenticationAttributes(HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        if (session == null) {
            return;
        }
        session.removeAttribute(WebAttributes.AUTHENTICATION_EXCEPTION);
    }

    private String determineTargetUrl(Authentication authentication) {
        return "/home";
//        boolean isUser = false;
//        boolean isAdmin = false;
//        Collection<? extends GrantedAuthority> authorities
//                = authentication.getAuthorities();
//        for (GrantedAuthority grantedAuthority : authorities) {
//            if (grantedAuthority.getAuthority().equals("USER")) {
//                isUser = true;
//                break;
//            } else if (grantedAuthority.getAuthority().equals("ADMIN")) {
//                isAdmin = true;
//                break;
//            }
//        }
//
//        if (isUser) {
//            return "/homepage.html";
//        } else
//
//        if (isAdmin) {
//            return "/admin/home";
//        } else {
//            throw new IllegalStateException();
//        }
    }

    public void resetUserValuesAfterLogin(String username) {
        User user = userService.findByUsername(username);
        if(user != null) {
            Driver driver = user.getDriver();
            //driver.setDriverStatus(DriverStatus.LOGIN);
            //driver.setChangeDriverStatusTime(LocalDateTime.now());
            //user.setWorkPlace(WorkPlace.NOWHERE);
            userService.updateWorkplace(username, Workplace.NOWHERE);
            //userService.save(user);
            logger.info("Reset user values.");
        }
    }
}
