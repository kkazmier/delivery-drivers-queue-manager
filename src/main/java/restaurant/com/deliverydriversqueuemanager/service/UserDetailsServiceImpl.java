package restaurant.com.deliverydriversqueuemanager.service;

import lombok.AllArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import restaurant.com.deliverydriversqueuemanager.model.User;
import restaurant.com.deliverydriversqueuemanager.repository.UserRepository;

import javax.management.relation.Role;
import javax.transaction.Transactional;
import java.util.HashSet;
import java.util.Set;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    @Transactional()
    public UserDetails loadUserByUsername(String username) {
        User user = userRepository.findByUsername(username);
        if (user == null) throw new UsernameNotFoundException(username);

        Set<GrantedAuthority> grantedAuthorities = new HashSet<>();
//        for (Role role : user.getRoles()){
//            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
//        }

        return new org.springframework.security.core.userdetails.User(user.getUsername(), user.getPassword(), grantedAuthorities);
    }
}
