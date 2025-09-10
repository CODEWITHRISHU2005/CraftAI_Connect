package com.CODEWITHRISHU.CraftAI_Connect.dto.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Set;

@Data
public class RegisterArtisianRequest {
    @NotBlank(message = "name is required")
    @Size(max = 50, message = "First name must not exceed 50 characters")
    private String artisanName;

    @NotBlank(message = "Craft specialty is required")
    @Size(max = 100, message = "Craft specialty must not exceed 100 characters")
    private String craftSpecialty;

    @Size(max = 2000, message = "Bio must not exceed 2000 characters")
    private String bio;

    @Size(max = 500, message = "Cultural background must not exceed 500 characters")
    private String culturalBackground;

    private Integer yearsOfExperience;

    private Set<String> skills;

    private String profileImageUrl;
}