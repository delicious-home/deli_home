package com.start.deli_home.Review.Service;

import com.start.deli_home.Member.Entity.Member;
import com.start.deli_home.Question.DataNotFoundException.DataNotFoundException;
import com.start.deli_home.Question.Entity.Question;
import com.start.deli_home.Review.Entity.Review;
import com.start.deli_home.Review.Repository.ReviewRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public void reviewCreate(Question question, String content, Member member,float rating){
        Review a = new Review();
        a.setContent(content);
        a.setQuestion(question);
        a.setAuthor(member);
        a.setRating(rating);
        a.setCreateDate(LocalDateTime.now());
        this.reviewRepository.save(a);
    }
    public Review getReview(Integer id) {
        Optional<Review> review = this.reviewRepository.findById(id);
        if (review.isPresent()) {
            return review.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }
    public void modify(Review review,String content,Float rating){

        review.setContent(content);
        review.setRating(rating);
        this.reviewRepository.save(review);
    }
    public void delete(Review review) {
        this.reviewRepository.delete(review);
    }
}
