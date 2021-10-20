package restaurant.com.deliverydriversqueuemanager.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.WebAttributes;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import restaurant.com.deliverydriversqueuemanager.util.ActiveUserStore;
import restaurant.com.deliverydriversqueuemanager.util.LoggedUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component("simpleAuthenticationSuccessHandler")
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(SimpleAuthenticationSuccessHandler.class);
    private RedirectStrategy redirectStrategy = new DefaultRedirectStrategy();
    ActiveUserStore activeUserStore;

    @Autowired
    public SimpleAuthenticationSuccessHandler(ActiveUserStore activeUserStore) {
        logger.info("Login handle initiated.");
        this.activeUserStore = activeUserStore;
    }

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        logger.info("onAuthenticationSuccess()");
        HttpSession session = request.getSession(false);
        if (session != null) {
            logger.info("Login: " + authentication.getName());
            logger.info("user store in null: " + (activeUserStore == null));
            LoggedUser user = new LoggedUser(authentication.getName(), activeUserStore);
            session.setAttribute("user", user);
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
}
