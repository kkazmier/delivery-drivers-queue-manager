package restaurant.com.deliverydriversqueuemanager.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import restaurant.com.deliverydriversqueuemanager.repository.DriverRepository;
import restaurant.com.deliverydriversqueuemanager.util.ActiveUserStore;
import restaurant.com.deliverydriversqueuemanager.util.LoggedUser;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component("simpleAuthenticationSuccessHandler")
public class SimpleAuthenticationSuccessHandler implements AuthenticationSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(SimpleAuthenticationSuccessHandler.class);
    @Autowired
    ActiveUserStore activeUserStore;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response, Authentication authentication)
            throws IOException {
        HttpSession session = request.getSession(false);
        if (session != null) {
            LoggedUser user = new LoggedUser(authentication.getName(), activeUserStore);
            logger.info("Add " + user + " to session.");
            session.setAttribute("user", user);
        }
    }
}
