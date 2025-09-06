package com.CODEWITHRISHU.CraftAI_Connect.service;

import com.CODEWITHRISHU.CraftAI_Connect.dto.ProductStatus;
import com.CODEWITHRISHU.CraftAI_Connect.dto.Request.AIStoryRequest;
import com.CODEWITHRISHU.CraftAI_Connect.dto.Request.ProductRequest;
import com.CODEWITHRISHU.CraftAI_Connect.dto.Response.AIStoryResponse;
import com.CODEWITHRISHU.CraftAI_Connect.entity.Product;
import com.CODEWITHRISHU.CraftAI_Connect.entity.User;
import com.CODEWITHRISHU.CraftAI_Connect.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;
    private final AIService aiService;

    public Product createProduct(ProductRequest request, User user) {
        Product product = Product.builder()
                .tittle(request.getTittle())
                .description(request.getDescription())
                .category(request.getCategory())
                .price(request.getPrice())
                .tags(request.getTags())
                .user(user)
                .status(ProductStatus.DRAFT)
                .stockQuantity(request.getStockQuantity())
                .build();

        return productRepository.save(product);
    }

    public ProductResponse getProductById(Long id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        // Increment view count
        product.setViews(product.getViews() + 1);
        productRepository.save(product);

        // Track analytics
        analyticsService.trackEvent(product.getUser().getId(), product.getId(), EventType.PRODUCT_VIEW);

        return convertToProductResponse(product);
    }

    public List<ProductResponse> getProductsByUser(Long userId) {
        List<Product> products = productRepository.findByUserId(userId);
        return products.stream()
                .map(this::convertToProductResponse)
                .toList();
    }

    public List<ProductResponse> searchProducts(String keyword) {
        List<Product> products = productRepository.searchProducts(keyword);
        return products.stream()
                .map(this::convertToProductResponse)
                .toList();
    }

    public Product generateAIStory(Long productId, AIStoryRequest request) {
        Product product = productRepository.findById(productId)
                .orElseThrow(() -> new RuntimeException("Product not found"));

        AIStoryResponse storyResponse = aiService.generateProductStory(request);
        product.setAiStory(storyResponse.getStory());

        if (storyResponse.getSuggestedTags() != null) {
            product.setTags(storyResponse.getSuggestedTags());
        }

        Product savedProduct = productRepository.save(product);

        // Track analytics
        analyticsService.trackEvent(product.getUser().getId(), productId, EventType.STORY_GENERATED);

        return savedProduct;
    }

}
