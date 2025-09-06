package com.CODEWITHRISHU.CraftAI_Connect.dto.Request;

import com.CODEWITHRISHU.CraftAI_Connect.entity.Address;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    @Email
    @NotBlank
    private String email;

    @NotBlank
    @Size(min = 6)
    private String password;

    @NotBlank
    private String name;

    private String role;

    private Address address;

    private String craftType;
    private Integer experienceYears;
    private String bio;
}
