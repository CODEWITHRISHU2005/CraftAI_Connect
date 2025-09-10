package com.CODEWITHRISHU.CraftAI_Connect.service;

import com.CODEWITHRISHU.CraftAI_Connect.dto.Request.AIStoryRequest;
import com.CODEWITHRISHU.CraftAI_Connect.dto.Response.AIStoryResponse;
import com.CODEWITHRISHU.CraftAI_Connect.entity.Product;
import com.CODEWITHRISHU.CraftAI_Connect.repository.AIContentRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import static com.CODEWITHRISHU.CraftAI_Connect.Utils.ObjectMapper.parseAIStoryResponse;

@Service
public class AIService {
    private final ChatClient chatClient;
    private final AIContentRepository aiContentRepository;
    private final ObjectMapper objectMapper;

    public AIService(ChatClient.Builder chatClientBuilder, AIContentRepository aiContentRepository, ObjectMapper objectMapper) {
        this.chatClient = chatClientBuilder.build();
        this.aiContentRepository = aiContentRepository;
        this.objectMapper = objectMapper;
    }

    public AIStoryResponse generateProductStory(AIStoryRequest request) {
        String storyPrompt = """
                Create a compelling and authentic story for this Indian handicraft product:

                Product Name: {productName}
                Craft Type: {craftType}
                Region: {region}
                Materials Used: {materials}
                Traditional Techniques: {techniques}
                Cultural Significance: {culturalSignificance}
                Tone: {tone}

                Requirements:
                1. Write an engaging story (200-300 words) that honors the craft's heritage
                2. Include the artisan's skill and dedication
                3. Mention traditional techniques and cultural importance
                4. Appeal to modern buyers while respecting tradition
                5. Use {tone} tone throughout
                6. Make it authentic and respectful to Indian culture

                Please respond in the following JSON format:
                {{
                    "story": "The complete story here...",
                    "title": "An engaging product title",
                    "suggestedTags": ["tag1", "tag2", "tag3", "tag4", "tag5"],
                    "seoDescription": "A 150-character SEO description"
                }}
                """;

        Prompt prompt = createPrompt(storyPrompt, Map.of(
                "productName", request.getProductName(),
                "craftType", request.getCraftType(),
                "region", request.getRegion() != null ? request.getRegion() : "India",
                "materials", request.getMaterials() != null ? request.getMaterials() : "traditional materials",
                "techniques", request.getTechniques() != null ? request.getTechniques() : "ancestral techniques",
                "culturalSignificance", request.getCulturalSignificance() != null ?
                        request.getCulturalSignificance() : "rich cultural heritage",
                "tone", request.getTone()
        ));

        ChatResponse response = chatClient.prompt(prompt);
        String aiResponse = response.getResult().getOutput().getText();

        return parseAIStoryResponse(aiResponse);
    }

    public String generateSocialMediaContent(Product product, String platform) {
        String socialPrompt = """
                Create engaging social media content for this Indian handicraft:

                Product: {name}
                Category: {category}
                Description: {description}
                Platform: {platform}

                Create content appropriate for {platform} with:
                - Engaging caption (max 280 chars for Twitter, max 2200 for Instagram)
                - Relevant hashtags (include Indian craft hashtags)
                - Call-to-action
                - Cultural respect and authenticity

                Return just the content, ready to post.
                """;

        Prompt prompt = createPrompt(socialPrompt, Map.of(
                "name", product.getName(),
                "category", product.getCategory(),
                "description", product.getDescription(),
                "platform", platform
        ));

        ChatResponse response = chatClient.prompt(prompt);
        return response.getResult().getOutput().getText();
    }

    public String generateProductDescription(String productName, String category, String features) {
        String descPrompt = """
                Create a compelling product description for this Indian handicraft:

                Product: {productName}
                Category: {category}
                Features: {features}

                Create a description that:
                - Highlights unique features and craftsmanship
                - Appeals to both domestic and international buyers
                - Includes cultural context appropriately
                - Is SEO-friendly with relevant keywords
                - Maintains authentic and respectful tone

                Keep it concise (100-150 words) but impactful.
                """;

        Prompt prompt = createPrompt(descPrompt, Map.of(
                "productName", productName,
                "category", category,
                "features", features
        ));

        ChatResponse response = chatClient.prompt(prompt);
        return response.getResult().getOutput().getText();
    }

    public List<String> generateSEOKeywords(Product product) {
        String keywordPrompt = """
        Generate SEO keywords for this Indian handicraft product:

        Product: {name}
        Category: {category}
        Description: {description}

        Generate 15-20 relevant keywords including:
        - Product-specific keywords
        - Craft technique keywords
        - Regional/cultural keywords
        - Material-based keywords
        - Occasion-based keywords

        Return as a JSON array of strings.
        """;

        Prompt prompt = createPrompt(keywordPrompt, Map.of(
                "name", product.getName(),
                "category", product.getCategory(),
                "description", product.getDescription()
        ));

        ChatResponse response = chatClient.prompt(prompt);
        String keywordsJson = response.getResult().getOutput().getText();

        try {
            return objectMapper.readValue(keywordsJson, new TypeReference<List<String>>() {});
        } catch (JsonProcessingException e) {
            // Log the error and return an empty list or handle it as needed
            return Collections.emptyList();
        }
    }

    private Prompt createPrompt(String promptTemplate, Map<String, Object> variables) {
        PromptTemplate template = new PromptTemplate(promptTemplate);
        return template.create(variables);
    }

}