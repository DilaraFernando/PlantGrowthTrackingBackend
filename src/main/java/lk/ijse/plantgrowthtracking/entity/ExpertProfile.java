package lk.ijse.plantgrowthtracking.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "expert_profiles")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class ExpertProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String specialization;//  WEATHER, PLANT PATHOLOGY

    @Column(columnDefinition = "TEXT")
    private String qualifications; // BSC IN AGRICULTURE,PHD IN BOTANY

    private String contactNumber;
    private String location;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}
