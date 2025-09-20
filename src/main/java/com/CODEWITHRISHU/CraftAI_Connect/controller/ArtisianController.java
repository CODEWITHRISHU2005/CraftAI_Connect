package com.CODEWITHRISHU.CraftAI_Connect.controller;

import com.CODEWITHRISHU.CraftAI_Connect.dto.Request.CreateArtisianRequest;
import com.CODEWITHRISHU.CraftAI_Connect.dto.Response.ArtisianResponse;
import com.CODEWITHRISHU.CraftAI_Connect.service.ArtisianService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/artisians")
@Validated
@RequiredArgsConstructor
public class ArtisianController {
    private final ArtisianService artisianService;

    @PostMapping
    public ResponseEntity<ArtisianResponse> createArtisan(@Valid @RequestBody CreateArtisianRequest request) {
            ArtisianResponse artisan = artisianService.createArtisan(request);
            return ResponseEntity.status(HttpStatus.CREATED).body(artisan);
    }

    @GetMapping
    public ResponseEntity<Page<ArtisianResponse>> searchArtisans(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String location,
            @RequestParam(required = false) String craftSpeciality,
            @PageableDefault(size = 20) Pageable pageable) {

        Page<ArtisianResponse> artisans = artisianService.searchArtisans(query, location, craftSpeciality, pageable);
        return ResponseEntity.ok(artisans);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ArtisianResponse> getArtisanById(@PathVariable Long id) {
        ArtisianResponse artisan = artisianService.getArtisanById(id);
        return ResponseEntity.ok(artisan);
    }

    @PostMapping("/{id}/enhance-profile")
    public ResponseEntity<Map<String, String>> enhanceProfile(@PathVariable Long id) {
        artisianService.enhanceArtisanProfile(id);
        return ResponseEntity.ok(Map.of("message", "Profile enhanced successfully"));
    }
}