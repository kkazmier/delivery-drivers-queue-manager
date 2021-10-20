package restaurant.com.deliverydriversqueuemanager.config;

import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import restaurant.com.deliverydriversqueuemanager.handler.SimpleLogoutSuccessHandler;
import restaurant.com.deliverydriversqueuemanager.service.UserDetailsServiceImpl;

@Configuration
@EnableWebSecurity
@NoArgsConstructor
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    //@Qualifier("userDetailsService")
    private UserDetailsServiceImpl userDetailsService;
    private SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler;
    private SimpleLogoutSuccessHandler simpleLogoutSuccessHandler;

    @Autowired
    public WebSecurityConfig(UserDetailsServiceImpl userDetailsService, SimpleUrlAuthenticationSuccessHandler simpleUrlAuthenticationSuccessHandler, SimpleLogoutSuccessHandler simpleLogoutSuccessHandler) {
        this.userDetailsService = userDetailsService;
        this.simpleUrlAuthenticationSuccessHandler = simpleUrlAuthenticationSuccessHandler;
        this.simpleLogoutSuccessHandler = simpleLogoutSuccessHandler;
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/login*", "/registration*").permitAll()
                .anyRequest().authenticated()
                .and().csrf().disable()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/login")
                .successHandler(simpleUrlAuthenticationSuccessHandler)
                .defaultSuccessUrl("/home", true)
                .permitAll()
                .and()
                .logout()
                .logoutSuccessHandler(simpleLogoutSuccessHandler)
                //.logoutUrl("/logout")
                .invalidateHttpSession(true)
                .logoutSuccessUrl("/logout")
                .deleteCookies("JSESSIONID")
                .permitAll();
    }

    @Bean
    public AuthenticationManager customAuthenticationManager() throws Exception {
        return authenticationManager();
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(bCryptPasswordEncoder());
    }
}
