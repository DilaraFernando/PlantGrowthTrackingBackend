package lk.ijse.plantgrowthtracking.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "farmer_profile")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FarmerProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String contactNumber;
    private String address;
    private String farmSize; // 1 ACRES ,HOME GARDEN

    @ElementCollection
    private List<String> interestedCrops;

    @OneToOne
    @JoinColumn(name = "user_id", referencedColumnName = "id")
    private User user;

}