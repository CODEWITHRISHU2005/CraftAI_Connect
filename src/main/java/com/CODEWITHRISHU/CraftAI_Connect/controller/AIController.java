package com.CODEWITHRISHU.CraftAI_Connect.controller;

import com.CODEWITHRISHU.CraftAI_Connect.dto.Request.AIStoryRequest;
import com.CODEWITHRISHU.CraftAI_Connect.dto.Response.AIStoryResponse;
import com.CODEWITHRISHU.CraftAI_Connect.entity.Product;
import com.CODEWITHRISHU.CraftAI_Connect.entity.User;
import com.CODEWITHRISHU.CraftAI_Connect.service.AIService;
import com.CODEWITHRISHU.CraftAI_Connect.service.ProductService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/AI")
@RequiredArgsConstructor
public class AIController {
    private final AIService aiService;
    private final ProductService productService;

    @PostMapping("/generate-story")
    public ResponseEntity<AIStoryResponse> generateStory(@Valid @RequestBody AIStoryRequest request) throws RuntimeException {
        AIStoryResponse response = aiService.generateProductStory(request);

        return ResponseEntity.ok(response);
    }

    @PostMapping("/products/{productId}/generate-story")
    public ResponseEntity<Product> generateStoryForProduct(@PathVariable Long productId,
                                                           @AuthenticationPrincipal User user,
                                                           @Valid @RequestBody AIStoryRequest request) throws RuntimeException {

        Product product = productService.generateAIStory(productId, request);
        return ResponseEntity.ok(product);
    }

    @PostMapping("/generate-description")
    public ResponseEntity<String> generateDescription(@RequestBody Map<String, String> request) {
        String description = aiService.generateProductDescription(
                request.get("productName"),
                request.get("category"),
                request.get("features")
        );
        return ResponseEntity.ok(description);
    }

    @PostMapping("/products/{productId}/social-content")
    public ResponseEntity<String> generateSocialContent(@PathVariable Long productId,
                                                        @RequestParam String platform) throws RuntimeException {
        String content = aiService.generateSocialMediaContent(productId, platform);
        return ResponseEntity.ok(content);
    }

//    @GetMapping("/products/{productId}/market-insights")
//    public ResponseEntity<MarketInsightResponse> getMarketInsights(@PathVariable Long productId) {
//        MarketInsightResponse insights = aiService.getMarketInsights(productId);
//        return ResponseEntity.ok(insights);
//    }

//    @PostMapping("/products/{productId}/seo-keywords")
//    public ResponseEntity<List<String>> generateSEOKeywords(@RequestParam Long productId) throws RuntimeException {
//        List<String> keywords = aiService.generateSEOKeywords(productId);
//        return ResponseEntity.ok(keywords);
//    }
}
