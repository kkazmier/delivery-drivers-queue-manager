package restaurant.com.deliverydriversqueuemanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "driver", schema = "public")
@Getter
@Setter
public class Driver extends BaseEntity {
    @JsonBackReference
    @OneToOne(mappedBy = "driver")
    private User user;

    private LocalDateTime changeDriverStatusTime;
    private String driverStatus;

    @Override
    public String toString() {
        return "Driver{" +
                "login=" + user.getUsername() +
                ", changeDriverStatusTime=" + changeDriverStatusTime +
                ", driverStatus='" + driverStatus + '\'' +
                '}';
    }
}
