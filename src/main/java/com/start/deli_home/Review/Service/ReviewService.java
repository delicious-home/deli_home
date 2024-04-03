package com.start.deli_home.Review.Service;

import com.start.deli_home.Question.Entity.Question;
import com.start.deli_home.Review.Entity.Review;
import com.start.deli_home.Review.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void reviewCreate(Question question, String content){
        Review a = new Review();
        a.setContent(content);
        a.setQuestion(question);
        a.setCreateDate(LocalDateTime.now());
        this.reviewRepository.save(a);

    }

}
