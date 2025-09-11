package com.CODEWITHRISHU.CraftAI_Connect.service;

import com.CODEWITHRISHU.CraftAI_Connect.Utils.ObjectMapper;
import com.CODEWITHRISHU.CraftAI_Connect.dto.ArtisianStatus;
import com.CODEWITHRISHU.CraftAI_Connect.dto.Request.CreateArtisianRequest;
import com.CODEWITHRISHU.CraftAI_Connect.dto.Response.ArtisianResponse;
import com.CODEWITHRISHU.CraftAI_Connect.entity.Artisian;
import com.CODEWITHRISHU.CraftAI_Connect.repository.ArtisianRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import static com.CODEWITHRISHU.CraftAI_Connect.Utils.ObjectMapper.artisianMapper;

@Service
@Slf4j
@RequiredArgsConstructor
public class ArtisianService {
    private final AIContentService aiContentService;
    private final ArtisianRepository artisianRepository;

    @Transactional
    public ArtisianResponse createArtisan(CreateArtisianRequest request) {
        if (artisianRepository.findByEmail(request.email()).isPresent()) {
            throw new RuntimeException("Artisan with email " + request.email() + " already exists");
        }

        Artisian artisan = new Artisian();
        artisan.setName(request.name());
        artisan.setEmail(request.email());
        artisan.setPhone(request.phone());
        artisan.setLocation(request.location());
        artisan.setSpecialization(request.specialization());
        artisan.setBio(request.bio());
        artisan.setYearsOfExperience(request.yearsOfExperience());
        artisan.setStatus(ArtisianStatus.ACTIVE);

        Artisian saved = artisianRepository.save(artisan);
        log.info("Created new artisan: {} with ID: {}", saved.getName(), saved.getId());

        return artisianMapper(saved);
    }

    public Page<ArtisianResponse> searchArtisans(String query, String location, String specialization, Pageable pageable) {
        Page<Artisian> artisans = artisianRepository.findBySearchCriteria(query, location, specialization, pageable);
        return artisans.map(ObjectMapper::artisianMapper);
    }

    public ArtisianResponse getArtisanById(Long id) {
        Artisian artisan = artisianRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Artisan not found with ID: " + id));
        return artisianMapper(artisan);
    }

    @Transactional
    public void enhanceArtisanProfile(Long artisanId) {
        Artisian artisan = artisianRepository.findById(artisanId)
                .orElseThrow(() -> new RuntimeException("Artisan not found"));

        if (artisan.getBio() == null || artisan.getBio().length() < 100) {
            String enhancedBio = aiContentService.generateArtisanBio(artisan);
            artisan.setBio(enhancedBio);
            log.info("Enhanced bio for artisan: {}", artisan.getName());
        }

        artisan.getProducts().forEach(product -> {
            if (product.getAiGeneratedDescription() == null) {
                String enhancedDescription = aiContentService.enhanceProductDescription(product);
                product.setAiGeneratedDescription(enhancedDescription);
                log.info("Enhanced description for product: {}", product.getName());
            }
        });

        artisianRepository.save(artisan);
    }
}