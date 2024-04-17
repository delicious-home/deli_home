package com.start.deli_home.Review.Controller;

import com.start.deli_home.Member.Entity.Member;
import com.start.deli_home.Member.Service.MemberService;
import com.start.deli_home.Question.Entity.Question;
import com.start.deli_home.Question.Service.QuestionService;
import com.start.deli_home.Review.Entity.Review;
import com.start.deli_home.Review.Repository.ReviewRepository;
import com.start.deli_home.Review.ReviewForm.ReviewForm;
import com.start.deli_home.Review.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final QuestionService questionService;
    private final ReviewService reviewService;
    private final MemberService memberService;
    private final ReviewRepository reviewRepository;

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create/{id}")
    public String answerDetail(Model model, @PathVariable("id") Integer id,
                               @Valid ReviewForm reviewForm, BindingResult bindingResult, Principal principal){
        Question question = this.questionService.getQuestion(id);
        if (bindingResult.hasErrors()){
            model.addAttribute("question", question);
            return "question_detail";
        }
        Member member = this.memberService.getMember(principal.getName());
        float rating = reviewForm.getRating();

        this.reviewService.reviewCreate(question,reviewForm.getContent(),member,rating);
        return String.format("redirect:/question/detail/%s", id);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String reviewModify(ReviewForm reviewForm, @PathVariable("id") Integer id, Principal principal) {
        Review review = this.reviewService.getReview(id);
        if (!review.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        reviewForm.setContent(review.getContent());
        reviewForm.setRating(review.getRating());
        return "review_form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String reviewModify(@Valid ReviewForm reviewForm, BindingResult bindingResult,
                               @PathVariable("id") Integer id, Principal principal) {
        if (bindingResult.hasErrors()) {
            return "review_form";
        }
        Review review = this.reviewService.getReview(id);
        if (!review.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.reviewService.modify(review, reviewForm.getContent(),reviewForm.getRating());
        return String.format("redirect:/question/detail/%s", review.getQuestion().getId());
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String reviewDelete(Principal principal, @PathVariable("id") Integer id) {
        Review review = this.reviewService.getReview(id);
        if (!review.getAuthor().getUsername().equals(principal.getName())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "삭제권한이 없습니다.");
        }
        this.reviewService.delete(review);
        return String.format("redirect:/question/detail/%s", review.getQuestion().getId());
    }
}