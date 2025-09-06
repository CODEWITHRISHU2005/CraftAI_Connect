package com.CODEWITHRISHU.CraftAI_Connect.Utils;

import com.CODEWITHRISHU.CraftAI_Connect.dto.Response.AIStoryResponse;

import java.util.Arrays;

public final class ObjectMapper {
    private ObjectMapper() {
    }

    public static AIStoryResponse parseAIStoryResponse(String jsonResponse) {
        AIStoryResponse response = new AIStoryResponse();
        response.setStory("Generated story from AI");
        response.setTitle("Generated title");
        response.setSuggestedTags(Arrays.asList("handmade", "traditional", "authentic"));
        response.setSeoDescription("Generated SEO description");
        return response;
    }

    public static MarketInsightResponse parseMarketInsightResponse(String jsonResponse) {
        MarketInsightResponse response = new MarketInsightResponse();
        return response;
    }

    public ProductResponse convertToProductResponse(Product product) {
        ProductResponse response = ProductResponse.builder()
                .id(product.getId())
                .title(product.getTitle())
                .description(product.getDescription())
                .aiStory(product.getAiStory())
                .category(product.getCategory())
                .price(product.getPrice())
                .images(product.getImages())
                .tags(product.getTags())
                .status(product.getStatus().toString())
                .views(product.getViews())
                .likes(product.getLikes())
                .artisanName(product.getUser().getName())
                .artisanLocation(product.getUser().getLocation())
                .createdAt(product.getCreatedAt().toString())
                .build();
        return response;

    }
}