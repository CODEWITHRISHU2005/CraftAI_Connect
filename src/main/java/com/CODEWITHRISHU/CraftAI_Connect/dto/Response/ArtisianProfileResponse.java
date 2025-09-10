package com.CODEWITHRISHU.CraftAI_Connect.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ArtisianProfileResponse {
    private String fullName;
    private String email;
    private String craftSpecialty;
    private String bio;
    private String culturalBackground;
    private Integer yearsOfExperience;
    private Set<String> skills;
}