package com.CODEWITHRISHU.CraftAI_Connect.service;

import com.CODEWITHRISHU.CraftAI_Connect.Utils.ObjectMapper;
import com.CODEWITHRISHU.CraftAI_Connect.dto.Request.GenerateStoryRequest;
import com.CODEWITHRISHU.CraftAI_Connect.dto.Response.StoryResponse;
import com.CODEWITHRISHU.CraftAI_Connect.dto.StoryType;
import com.CODEWITHRISHU.CraftAI_Connect.entity.Artisian;
import com.CODEWITHRISHU.CraftAI_Connect.entity.Product;
import com.CODEWITHRISHU.CraftAI_Connect.entity.Story;
import com.CODEWITHRISHU.CraftAI_Connect.repository.ArtisianRepository;
import com.CODEWITHRISHU.CraftAI_Connect.repository.ProductRepository;
import com.CODEWITHRISHU.CraftAI_Connect.repository.StoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

import static com.CODEWITHRISHU.CraftAI_Connect.Utils.ObjectMapper.storyMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class StoryService {
    private final StoryRepository storyRepository;
    private final ArtisianRepository artisianRepository;
    private final ProductRepository productRepository;
    private final AIContentService aiContentService;

    @Transactional
    public StoryResponse generateStory(Long artisanId, GenerateStoryRequest request) {
        Artisian artisan = artisianRepository.findById(artisanId)
                .orElseThrow(() -> new RuntimeException("Artisan not found"));

        Product product = null;
        if (request.productId() != null) {
            product = productRepository.findById(request.productId())
                    .orElseThrow(() -> new RuntimeException("Product not found"));
        }

        String storyContent = aiContentService.generateCraftStory(artisan, product, request.type(), request.additionalContext());
        String title = generateTitleFromType(request.type(), artisan, product);

        Story story = new Story();
        story.setTitle(title);
        story.setContent(storyContent);
        story.setType(request.type());
        story.setArtisan(artisan);
        story.setProduct(product);
        story.setIsAiGenerated(true);
        story.setAiPromptUsed(String.format("Type: %s, Context: %s", request.type(), request.additionalContext()));

        Story saved = storyRepository.save(story);
        log.info("Generated story for artisan {}: {}", artisan.getName(), title);

        return storyMapper(saved);
    }

    public List<StoryResponse> getStoriesByArtisan(Long artisanId) {
        List<Story> stories = storyRepository.findByArtisanIdOrderByCreatedAtDesc(artisanId);
        return stories.stream().map(ObjectMapper::storyMapper).collect(Collectors.toList());
    }

    private String generateTitleFromType(StoryType type, Artisian artisan, Product product) {
        return switch (type) {
            case CRAFT_ORIGIN -> String.format("The Origins of %s: A Traditional Craft", artisan.getSpecialization());
            case TECHNIQUE -> String.format("Mastering the Art: %s Techniques", artisan.getSpecialization());
            case CULTURAL_HERITAGE -> String.format("Cultural Heritage: The Story of %s Crafts", artisan.getLocation());
            case PERSONAL_JOURNEY -> String.format("The Journey of %s: A Master Artisan", artisan.getName());
        };
    }
}
