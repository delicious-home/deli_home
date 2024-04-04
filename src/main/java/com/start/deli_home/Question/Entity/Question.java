package com.start.deli_home.Question.Entity;

import com.start.deli_home.Review.Entity.Review;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@Entity
public class Question {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

    @Column(length = 200)
    private String subject;

    @Column(columnDefinition = "TEXT")
    private String content;

    @OneToMany(mappedBy = "question", cascade = CascadeType.REMOVE)
    private List<Review> reviewList;

    public void setCreateDate(LocalDateTime now) {
    }

    private String category;

    @Column(name = "create_date")
    private LocalDateTime createDate;

}