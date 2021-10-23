package restaurant.com.deliverydriversqueuemanager.model;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "driver_id", referencedColumnName = "id")
    @JsonManagedReference
    private Driver driver;
}
