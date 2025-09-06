package com.CODEWITHRISHU.CraftAI_Connect.repository;

import com.CODEWITHRISHU.CraftAI_Connect.dto.ProductStatus;
import com.CODEWITHRISHU.CraftAI_Connect.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
    List<Product> findByUserId(Long userId);
    List<Product> findByCategory(String category);
    List<Product> findByStatus(ProductStatus status);

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' ORDER BY p.createdAt DESC")
    List<Product> findActiveProducts();

    @Query("SELECT p FROM Product p WHERE p.status = 'ACTIVE' AND " +
            "(LOWER(p.title) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            "LOWER(p.description) LIKE LOWER(CONCAT('%', :keyword, '%')))")
    List<Product> searchProducts(@Param("keyword") String keyword);

    @Query("SELECT DISTINCT p.category FROM Product p WHERE p.status = 'ACTIVE'")
    List<String> findAllCategories();
}