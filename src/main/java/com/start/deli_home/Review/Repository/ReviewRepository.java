package com.start.deli_home.Review.Repository;

import com.start.deli_home.Review.Entity.Review;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReviewRepository extends JpaRepository<Review, Integer> {
}