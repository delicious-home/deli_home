package com.start.deli_home.Question.Controller;


import com.start.deli_home.Question.Entity.Question;
import com.start.deli_home.Question.Service.QuestionService;
import com.start.deli_home.Question.QuestionForm.QuestionForm;
import com.start.deli_home.Review.ReviewForm.ReviewForm;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;

    @GetMapping("/list")
    public String questionList(Model model,
                               @RequestParam(value = "page", defaultValue = "0") int page,
                               @RequestParam(value = "category", required = false) String category,
                               @RequestParam(value = "searchKeyword", required = false) String searchKeyword) {
        Page<Question> list;

        if (searchKeyword != null && !searchKeyword.isEmpty()) {
            list = questionService.boardSearchList(searchKeyword, PageRequest.of(page, 10, Sort.Direction.DESC, "id"));
        } else {
            list = questionService.getList(page, category);
        }

        model.addAttribute("paging", list);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("searchKeyword", searchKeyword);
        return "question_list";
    }
    @GetMapping("/detail/{id}")
    public String questionDetail(Model model, @PathVariable("id") Integer id, ReviewForm reviewForm){
        Question question = this.questionService.getQuestion(id);
        model.addAttribute("question", question);
        return "question_detail";
    }
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form";
    }
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 @RequestParam("images") List<MultipartFile> images){
        if (bindingResult.hasErrors()){
            return "question_form";
        }
        this.questionService.create(questionForm.getSubject(), questionForm.getContent(),questionForm.getCategory(),images,
                questionForm.getAddress(),questionForm.getIntroduce(),questionForm.getTime(),questionForm.getMenu(),
                questionForm.getPhone(),questionForm.getShopName(),questionForm.getFoodType());
        return "redirect:/question/list";
    }
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id) {
        Question question = this.questionService.getQuestion(id);

        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        return "question_form";
    }
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 @PathVariable("id") Integer id) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);

        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent());
        return String.format("redirect:/question/detail/%s", id);
    }
    @GetMapping("/delete/{id}")
    public String questionDelete(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 @PathVariable("id")Integer id){
        Question question = this.questionService.getQuestion(id);
        this.questionService.delete(question);
        return "redirect:/question/list";
    }
}