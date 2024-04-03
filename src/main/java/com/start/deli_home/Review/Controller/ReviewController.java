package com.start.deli_home.Review.Controller;

import com.start.deli_home.Question.Entity.Question;
import com.start.deli_home.Question.Service.QuestionService;
import com.start.deli_home.Review.ReviewForm.ReviewForm;
import com.start.deli_home.Review.Service.ReviewService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/review")
@RequiredArgsConstructor
public class ReviewController {

    private final QuestionService questionService;
    private final ReviewService reviewService;

    @PostMapping("/create/{id}")
    public String answerDetail(Model model, @PathVariable("id") Integer id,
                               @Valid ReviewForm reviewForm, BindingResult bindingResult){
        Question question = this.questionService.getQuestion(id);
        if (bindingResult.hasErrors()){
            model.addAttribute("question", question);
            return "question_detail";
        }
        this.reviewService.reviewCreate(question,reviewForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }


}