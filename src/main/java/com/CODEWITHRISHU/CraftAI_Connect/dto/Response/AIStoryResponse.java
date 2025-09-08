package com.CODEWITHRISHU.CraftAI_Connect.dto.Response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AIStoryResponse {
    private String story;
    private String tittle;
    private List<String> suggestedTags;
    private String description;
}
