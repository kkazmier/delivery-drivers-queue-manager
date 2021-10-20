package restaurant.com.deliverydriversqueuemanager.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.List;

@Component
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoggedUser implements HttpSessionBindingListener {
    private static final Logger logger = LoggerFactory.getLogger(LoggedUser.class);
    private String username;

    @Autowired
    private ActiveUserStore activeUserStore;

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
        logger.info("valueBound()");
        List<String> users;
        users = activeUserStore.getUsers();
        if(activeUserStore == null){
            activeUserStore = new ActiveUserStore();
            logger.info("user store is null");
        } else {
            users = activeUserStore.getUsers();
            logger.info("Active users: " + users.size());
        }
        LoggedUser user = (LoggedUser) event.getValue();
        if (!users.contains(user.getUsername())) {
            users.add(user.getUsername());
            logger.info("add user " + user.username + " to userstore");
        }
        logger.info("[login] logged user count: " + users.size());
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
        logger.info("valueUnbound()");
        List<String> users = activeUserStore.getUsers();
        LoggedUser user = (LoggedUser) event.getValue();
        logger.info("try logout: " + user.username);
        if (users.contains(user.getUsername())) {
            users.remove(user.getUsername());
            logger.info("remove user: " + user.getUsername() + " from userstore");
        }
        logger.info("[logout] logged user count: " + users.size());
    }
}