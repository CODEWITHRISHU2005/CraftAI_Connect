package com.CODEWITHRISHU.CraftAI_Connect.entity;

import com.CODEWITHRISHU.CraftAI_Connect.dto.ArtisianStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "artisans")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Artisian extends AuditEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(unique = true, nullable = false)
    private String email;

    private String craftSpecialty;

    @Column(columnDefinition = "TEXT")
    private String bio;

    private Integer yearsOfExperience;

    @Enumerated(EnumType.STRING)
    private ArtisianStatus status = ArtisianStatus.ACTIVE;

    private String profileImageUrl;

    @OneToMany(mappedBy = "artisan", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    @OneToMany(mappedBy = "artisian", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Story> stories = new ArrayList<>();
}