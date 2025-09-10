package com.CODEWITHRISHU.CraftAI_Connect.entity;

import com.CODEWITHRISHU.CraftAI_Connect.dto.ProductStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name = "products")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Product extends AuditEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(max = 100)
    @Column(name = "product_name", nullable = false)
    private String productName;

    @Column(length = 2000, columnDefinition = "TEXT")
    private String baseDescription;

    @Column(length = 3000, columnDefinition = "TEXT")
    private String generatedDescription;

    private Set<String> category;

    @NotNull
    @DecimalMin(value = "0.0", inclusive = false)
    private BigDecimal price;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "artisan_id", nullable = false)
    private Artisian artisan;

    @Column(name = "material")
    private Set<String> materials = new HashSet<>();

    private Set<String> techniques = new HashSet<>();

    @Column(name = "image_url")
    private Set<String> imageUrls = new HashSet<>();

    @Column(name = "stock_quantity")
    private Integer stockQuantity = 1;

    @Column(name = "weight_grams")
    private Integer weightGrams;

    @Size(max = 500)
    @Column(name = "dimensions")
    private String dimensions;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private ProductStatus status = ProductStatus.ACTIVE;

    @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Story> stories = new ArrayList<>();
}