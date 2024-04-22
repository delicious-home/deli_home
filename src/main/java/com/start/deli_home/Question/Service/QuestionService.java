package com.start.deli_home.Question.Service;

import com.start.deli_home.Member.Entity.Member;
import com.start.deli_home.Question.DataNotFoundException.DataNotFoundException;
import com.start.deli_home.Question.Entity.Question;
import com.start.deli_home.Question.Repository.QuestionRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

    @Value("${custom.fileDirPath}")
    private String fileDirPath;

    public List<Question> getList() {

        return this.questionRepository.findAll();
    }

    public Question getQuestion(Integer id) {
        Optional<Question> question = this.questionRepository.findById(id);
        if (question.isPresent()) {
            return question.get();
        } else {
            throw new DataNotFoundException("question not found");
        }
    }

    public void create(String subject, String content, String category, List<MultipartFile> images,
                       String address, String introduce, String time,
                       String menu, String phone,
                       String shopName, String foodType, Member member) {
        List<String> imagePaths = new ArrayList<>();

        for (MultipartFile image : images) {
            String imageRelPath = "article/" + UUID.randomUUID().toString() + ".jpg";
            File imageFile = new File(fileDirPath + "/" + imageRelPath);

            try {
                image.transferTo(imageFile);
                imagePaths.add(imageRelPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        Question q = new Question();
        q.setContent(content);
        q.setSubject(subject);
        q.setAddress(address);
        q.setIntroduce(introduce);
        q.setTime(time);
        q.setMenu(menu);
        q.setPhone(phone);
        q.setShopName(shopName);
        q.setFoodType(foodType);
        q.setImages(imagePaths);
        q.setAuthor(member);
        q.setCategory(category);

        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }

    public Page<Question> getList(int page, String category) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createDate").ascending());
        if (category != null && !category.isEmpty()) {
            return this.questionRepository.findByCategory(category, pageable);
        } else {
            return this.questionRepository.findAll(pageable);
        }
    }

    public void modify(Question question, String subject, String content,String category, List<MultipartFile> images,
                       String address, String introduce, String time,
                       String menu, String phone,
                       String shopName, String foodType, Member member) {

        List<String> imagePaths = new ArrayList<>();

        for (MultipartFile image : images) {
            String imageRelPath = "article/" + UUID.randomUUID().toString() + ".jpg";
            File imageFile = new File(fileDirPath + "/" + imageRelPath);

            try {
                image.transferTo(imageFile);
                imagePaths.add(imageRelPath);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }


        question.setSubject(subject);
        question.setContent(content);
        question.setAddress(address);
        question.setIntroduce(introduce);
        question.setTime(time);
        question.setMenu(menu);
        question.setPhone(phone);
        question.setShopName(shopName);
        question.setFoodType(foodType);
        question.setImages(imagePaths);
        question.setCategory(category);
        this.questionRepository.save(question);
    }
    public void delete(Question question){
        questionRepository.delete(question);
    }
    // 게시글 리스트 처리
    public Page<Question> boardList(Pageable pageable) {
        return questionRepository.findAll(pageable);
    }

    public Page<Question> boardSearchList(String searchKeyword, Pageable pageable) { // 검색 기능 추가
        return questionRepository.findBySubjectContaining(searchKeyword, pageable);
    }
    public void like (Question question,Member member) {
        question.getLikers().add(member);
        question.setLikedByCurrentUser(true);
        this.questionRepository.save(question);
    }
    public void unLike (Question question,Member member) {
        question.getLikers().remove(member);
        question.setLikedByCurrentUser(false);
        this.questionRepository.save(question);
    }
    public void questionSave (Question question) {

    }

}