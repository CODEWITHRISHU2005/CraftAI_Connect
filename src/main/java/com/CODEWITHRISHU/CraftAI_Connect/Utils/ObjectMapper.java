package com.CODEWITHRISHU.CraftAI_Connect.Utils;

import com.CODEWITHRISHU.CraftAI_Connect.dto.Response.AIStoryResponse;

import java.util.Arrays;

public final class ObjectMapper {
    private ObjectMapper() {
        throw new UnsupportedOperationException("Utility class");
    }

    public static AIStoryResponse parseAIStoryResponse(String jsonResponse) {
        return AIStoryResponse.builder()
                .story("AI Generated story")
                .tittle("AI Generated tittle")
                .description("AI Generated description")
                .suggestedTags(Arrays.asList("tag1", "tag2", "tag3"))
                .build();
    }

//    public static MarketInsightResponse parseMarketInsightResponse(String jsonResponse) {
//        MarketInsightResponse response = new MarketInsightResponse();
//        return response;
//    }
//
//    public ProductResponse convertToProductResponse(Product product) {
//        ProductResponse response = ProductResponse.builder()
//                .id(product.getId())
//                .title(product.getTitle())
//                .description(product.getDescription())
//                .aiStory(product.getAiStory())
//                .category(product.getCategory())
//                .price(product.getPrice())
//                .images(product.getImages())
//                .tags(product.getTags())
//                .status(product.getStatus().toString())
//                .views(product.getViews())
//                .likes(product.getLikes())
//                .artisanName(product.getUser().getName())
//                .artisanLocation(product.getUser().getLocation())
//                .createdAt(product.getCreatedAt().toString())
//                .build();
//        return response;
//    }
}