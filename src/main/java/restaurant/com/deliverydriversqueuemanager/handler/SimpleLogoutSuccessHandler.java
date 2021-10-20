package restaurant.com.deliverydriversqueuemanager.handler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@Component("simpleLogoutSuccessHandler")
public class SimpleLogoutSuccessHandler implements LogoutSuccessHandler {
    private static final Logger logger = LoggerFactory.getLogger(SimpleLogoutSuccessHandler.class);
    @Override
    public void onLogoutSuccess(HttpServletRequest request,
                                HttpServletResponse response, Authentication authentication)
            throws IOException, ServletException {
        logger.info("onLogoutSuccess()");
        HttpSession session = request.getSession();
        if (session != null){
            session.removeAttribute("user");
        }
    }
}
