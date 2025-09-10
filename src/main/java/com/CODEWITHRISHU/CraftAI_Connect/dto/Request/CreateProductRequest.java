package com.CODEWITHRISHU.CraftAI_Connect.dto.Request;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Set;

@Data
public class CreateProductRequest {
    @NotBlank(message = "Product name is required")
    @Size(max = 200, message = "Product name must not exceed 200 characters")
    private String productName;

    @Size(max = 2000, message = "Description must not exceed 2000 characters")
    @NotBlank(message = "Description is required")
    private String baseDescription;

    @NotNull(message = "Price is required")
    @DecimalMin(value = "0.0", inclusive = false, message = "Price must be greater than 0")
    private BigDecimal price;

    private Set<String> category;

    private Set<String> materials;

    private Set<String> techniques;

    private Set<String> imageUrls;

    @NotNull(message = "Stock quantity is required")
    private Integer stockQuantity = 1;

    @NotNull(message = "wight in grams is required")
    private Integer weightGrams;

    private String dimensions;
}