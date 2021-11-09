package restaurant.com.deliverydriversqueuemanager.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSessionBindingEvent;
import javax.servlet.http.HttpSessionBindingListener;
import java.util.List;

@Component
@NoArgsConstructor
@Getter
@Setter
public class LoggedUser implements HttpSessionBindingListener {
    private static final Logger logger = LoggerFactory.getLogger(LoggedUser.class);
    private String username;
    private ActiveUserStore activeUserStore;

    public LoggedUser(String username, ActiveUserStore activeUserStore) {
        this.username = username;
        this.activeUserStore = activeUserStore;
    }

    @Override
    public void valueBound(HttpSessionBindingEvent event) {
//        List<String> users = activeUserStore.getUsers();
//        LoggedUser user = (LoggedUser) event.getValue();
//        if (!users.contains(user.getUsername())) {
//            users.add(user.getUsername());
//        }
    }

    @Override
    public void valueUnbound(HttpSessionBindingEvent event) {
//        List<String> users = activeUserStore.getUsers();
//        LoggedUser user = (LoggedUser) event.getValue();
//        if (users.contains(user.getUsername())) {
//            users.remove(user.getUsername());
//        }
    }

    @Override
    public String toString() {
        return "LoggedUser{" +
                "username='" + username + '\'' +
                ", activeUserStore=" + activeUserStore +
                '}';
    }
}
