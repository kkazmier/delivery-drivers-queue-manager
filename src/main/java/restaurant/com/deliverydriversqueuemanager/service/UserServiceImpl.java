package restaurant.com.deliverydriversqueuemanager.service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import restaurant.com.deliverydriversqueuemanager.model.Driver;
import restaurant.com.deliverydriversqueuemanager.model.User;
import restaurant.com.deliverydriversqueuemanager.repository.DriverRepository;
import restaurant.com.deliverydriversqueuemanager.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@AllArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private static final Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    private UserRepository userRepository;
    //private RoleRepository roleRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Override
    public void create(User user) {

    }

    @Override
    public void save(User user) {
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        //user.setRoles(new HashSet<>((Collection<? extends Role>) roleRepository.findAll()));
        userRepository.save(user);
    }

    @Override
    public User findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public void setWorkplace(String username, String workplace) {
        User user = userRepository.findByUsername(username);
        if(user != null) {
            user.setWorkPlace(workplace);
            userRepository.save(user);
            logger.info("Set workplace: " + workplace);
        }
    }

    @Override
    public void updateWorkplace(String username, String workplace) {
        userRepository.updateWorkplace(username, workplace);
    }

    @Override
    public String getFullNameUser(String username) {
        logger.info("getFullNameUser()");
        User user = userRepository.findByUsername(username);
        String fullName = "";
        if (user != null) {
            fullName = user.getFirstName() + " " + user.getLastName();
            logger.info("Full name: " + fullName);
        }
        return fullName;
    }
}
