package com.CODEWITHRISHU.CraftAI_Connect.dto.Request;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AIStoryRequest {
    @NotBlank
    private String productName;

    @NotBlank
    private String craftType;

    private String region;
    private String materials;
    private String techniques;
    private String culturalSignificance;
    private String tone = "engaging"; // engaging, formal, poetic
}
