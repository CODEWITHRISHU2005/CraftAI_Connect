package com.CODEWITHRISHU.CraftAI_Connect.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "artisans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Artisian extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "name is required")
    @Size(max = 50, message = "name must be at most 50 characters")
    @Column(name = "artisan_name", nullable = false)
    private String artisanName;

    @OneToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @NotBlank
    @Size(max = 100)
    @Column(name = "craft_specialty")
    private String craftSpecialty;

    @Column(name = "bio", length = 2000)
    private String bio;

    private String culturalBackground;

    @Column(name = "years_of_experience")
    private Integer yearsOfExperience;

    private Set<String> skills = new HashSet<>();

    @Column(name = "profile_image_url")
    private String profileImageUrl;

    @OneToMany(mappedBy = "artisan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "artisan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Story> stories = new ArrayList<>();

    @OneToMany(mappedBy = "artisan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<MarketingIdea> marketingIdeas = new ArrayList<>();
}