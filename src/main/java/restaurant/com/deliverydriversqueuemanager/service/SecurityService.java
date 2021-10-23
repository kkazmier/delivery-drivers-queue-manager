package restaurant.com.deliverydriversqueuemanager.service;

public interface SecurityService {
    boolean isAuthenticated();
    void autoLogin(String login, String password);
}
