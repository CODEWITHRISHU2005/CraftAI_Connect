package com.CODEWITHRISHU.CraftAI_Connect.dto.Request;

import lombok.Data;

import java.util.List;

@Data
public class ProductRequest {
    private String tittle;
    private String description;
    private double price;
    private String category;
    private int stockQuantity;
    private List<String> tags;
}