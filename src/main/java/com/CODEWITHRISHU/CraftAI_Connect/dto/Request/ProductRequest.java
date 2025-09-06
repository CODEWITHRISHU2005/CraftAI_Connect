package com.CODEWITHRISHU.CraftAI_Connect.dto.Request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String tittle;
    private String description;
    private double price;
    private String category;
    private int stockQuantity;
    private List<String> tags;
}
