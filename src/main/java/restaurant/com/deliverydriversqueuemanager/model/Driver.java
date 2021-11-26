package restaurant.com.deliverydriversqueuemanager.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import java.time.LocalDateTime;

@Entity
@Table(name = "drivers", schema = "public")
@Getter
@Setter
public class Driver extends BaseEntity {
    public Driver() {
        driverStatus = DriverStatus.UNDEFINED;
    }

    @JsonBackReference
    @OneToOne(mappedBy = "driver", cascade = CascadeType.ALL)
    private User user;

    private LocalDateTime changeDriverStatusTime;
    private String changeDriverStatusTimeStr;
    private String driverStatus;

    @Override
    public String toString() {
        return "Driver{" +
                "user=" + user.getUsername() +
                ", changeDriverStatusTime=" + changeDriverStatusTime +
                ", changeDriverStatusTimeStr='" + changeDriverStatusTimeStr + '\'' +
                ", driverStatus='" + driverStatus + '\'' +
                '}';
    }
}
