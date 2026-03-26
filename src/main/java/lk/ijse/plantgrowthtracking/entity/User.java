package lk.ijse.plantgrowthtracking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private String username;

    @Column(nullable = false)
    private String role = "USER";
    
    // User settings
    @Column(nullable = true)
    private String timezone = "UTC";

    @Column(nullable = true)
    private String locale = "en_US";

    @Column(nullable = true)
    private Boolean notifyByEmail = true;
    
    @Column(nullable = true)
    private String displayName;

    @Column(nullable = true)
    private String units = "metric";

    @Column(nullable = true)
    private Boolean darkMode = false;

    @Column(nullable = true)
    private Boolean pushNotifications = false;
}