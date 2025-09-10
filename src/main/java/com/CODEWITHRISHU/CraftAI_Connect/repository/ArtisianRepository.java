package com.CODEWITHRISHU.CraftAI_Connect.repository;

import com.CODEWITHRISHU.CraftAI_Connect.entity.Artisian;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ArtisianRepository extends JpaRepository<Artisian, Long> {
    Optional<Artisian> findByUserId(Long userId);

    @Query("SELECT a FROM Artisan a WHERE a.isVerified = true")
    List<Artisian> findAllVerified();

    @Query("SELECT a FROM Artisan a WHERE " +
            "(:craft IS NULL OR LOWER(a.craftSpecialty) LIKE LOWER(CONCAT('%', :craft, '%'))) AND " +
            "(:location IS NULL OR LOWER(a.workshopLocation) LIKE LOWER(CONCAT('%', :location, '%')))")
    Page<Artisian> searchArtisans(@Param("craft") String craft,
                                  @Param("location") String location,
                                  Pageable pageable);

    @Query("SELECT a FROM Artisan a WHERE a.rating >= :minRating ORDER BY a.rating DESC")
    List<Artisian> findTopRatedArtisans(@Param("minRating") Double minRating, Pageable pageable);
}