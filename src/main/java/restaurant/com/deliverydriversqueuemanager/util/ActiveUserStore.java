package restaurant.com.deliverydriversqueuemanager.util;

import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@Scope("singleton")
@Getter
@Setter
public class ActiveUserStore {
    private static final Logger logger = LoggerFactory.getLogger(ActiveUserStore.class);
    public final List<String> users;

    public ActiveUserStore() {
        logger.info("User store created.");
        users = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "ActiveUserStore{" +
                "users=" + users +
                '}';
    }
}
