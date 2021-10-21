package restaurant.com.deliverydriversqueuemanager.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;
import java.time.LocalDateTime;

@Entity
@Table(name = "users")
@Getter
@Setter
public class User extends BaseEntity {
    private String username;

    private String password;

    @Transient
    private String passwordConfirm;

    private Boolean isLogged;

    private String driverStatus;

    private LocalDateTime changeDriverStatusTime;
}
