package com.CODEWITHRISHU.CraftAI_Connect.repository;

import com.CODEWITHRISHU.CraftAI_Connect.entity.Story;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface StoryRepository extends JpaRepository<Story, Long> {
    List<Story> findByArtisanIdAndIsPublished(Long artisanId, Boolean isPublished);

    List<Story> findByProductIdAndIsPublished(Long productId, Boolean isPublished);

    @Query("SELECT s FROM Story s WHERE s.storyType = :storyType AND s.isPublished = true")
    Page<Story> findByStoryType(@Param("storyType") Story.storyType storyType, Pageable pageable);

    @Query("SELECT s FROM Story s WHERE s.isPublished = true ORDER BY s.viewCount DESC")
    List<Story> findPopularStories(Pageable pageable);
}