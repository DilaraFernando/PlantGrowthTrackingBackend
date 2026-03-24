package lk.ijse.plantgrowthtracking.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.*;

import javax.management.relation.Role;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    private String username;
    private String email;
    private String password;
    private Role role;  //USER,FARMER,EXPERT
}
