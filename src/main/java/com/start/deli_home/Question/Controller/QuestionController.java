package com.start.deli_home.Question.Controller;


import com.start.deli_home.Member.Entity.Member;
import com.start.deli_home.Member.Service.MemberService;
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
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/question")
public class QuestionController {

    private final QuestionService questionService;
    private final MemberService memberService;

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
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/create")
    public String questionCreate(QuestionForm questionForm){
        return "question_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/create")
    public String questionCreate(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 @RequestParam("images") List<MultipartFile> images, Principal principal){
        if (bindingResult.hasErrors()){
            return "question_form";
        }
        Member member = this.memberService.getMember(principal.getName());

        this.questionService.create(questionForm.getSubject(), questionForm.getContent(),questionForm.getCategory(),images,
                questionForm.getAddress(),questionForm.getIntroduce(),questionForm.getTime(),questionForm.getMenu(),
                questionForm.getPhone(),questionForm.getShopName(),questionForm.getFoodType(),member);
        return "redirect:/question/list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/modify/{id}")
    public String questionModify(QuestionForm questionForm, @PathVariable("id") Integer id,Principal principal) {
        Question question = this.questionService.getQuestion(id);

        if (!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        questionForm.setSubject(question.getSubject());
        questionForm.setContent(question.getContent());
        questionForm.setCategory(question.getCategory());
        questionForm.setImages(new ArrayList<>());
        questionForm.setAddress(question.getAddress());
        questionForm.setIntroduce(question.getIntroduce());
        questionForm.setTime(question.getTime());
        questionForm.setMenu(question.getTime());
        questionForm.setPhone(question.getPhone());
        questionForm.setMenu(question.getMenu());
        questionForm.setPhone(question.getPhone());
        questionForm.setShopName(question.getShopName());
        questionForm.setFoodType(question.getFoodType());
        return "question_form";
    }
    @PreAuthorize("isAuthenticated()")
    @PostMapping("/modify/{id}")
    public String questionModify(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 @PathVariable("id") Integer id,Principal principal,@RequestParam("images") List<MultipartFile> images) {
        if (bindingResult.hasErrors()) {
            return "question_form";
        }
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }

        Member member = this.memberService.getMember(principal.getName());
        this.questionService.modify(question, questionForm.getSubject(), questionForm.getContent(),questionForm.getCategory(),images,
                questionForm.getAddress(),questionForm.getIntroduce(),questionForm.getTime(),questionForm.getMenu(),
                questionForm.getPhone(),questionForm.getShopName(),questionForm.getFoodType(),member);
        return String.format("redirect:/question/detail/%s", id);
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/delete/{id}")
    public String questionDelete(@Valid QuestionForm questionForm, BindingResult bindingResult,
                                 @PathVariable("id")Integer id,Principal principal){
        Question question = this.questionService.getQuestion(id);
        if (!question.getAuthor().getUsername().equals(principal.getName())){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "수정권한이 없습니다.");
        }
        this.questionService.delete(question);
        return "redirect:/question/list";
    }
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/like/{id}")
    public String likeOrUnlikeQuestion(@PathVariable("id") Integer id, Principal principal) {
        Member member = this.memberService.getMember(principal.getName());
        Question question = this.questionService.getQuestion(id);

        if (question != null && member != null) {
            if (question.getLikers().contains(member)) {
                this.questionService.unLike(question, member);
                question.setLikedByCurrentUser(false);
            } else {
                this.questionService.like(question, member);
                question.setLikedByCurrentUser(true);
            }
            this.questionService.questionSave(question);
        }
        return String.format("redirect:/question/detail/%s", id);
    }
}