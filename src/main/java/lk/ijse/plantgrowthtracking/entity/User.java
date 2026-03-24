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

    @Column(nullable = false)
    private String username;

    @Column(unique = true, nullable = false)
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)

    private Role role; //EXPERT,FARMER

    private String authProvider;
    private String providerId;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private FarmerProfile farmerProfile;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private ExpertProfile expertProfile;
}