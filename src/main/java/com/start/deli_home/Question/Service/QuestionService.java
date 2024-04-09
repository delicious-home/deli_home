package com.start.deli_home.Question.Service;

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

    public void create(String subject, String content, String category, MultipartFile thumbnail) {
        String thumbnailRelPath = "article/" + UUID.randomUUID().toString() + ".jpg";
        File thumbnailFile = new File(fileDirPath + "/" + thumbnailRelPath);

        try {
            thumbnail.transferTo(thumbnailFile);
        } catch (IOException e) {
            throw  new RuntimeException(e);
        }

        Question q = new Question();
        q.setContent(content);
        q.setSubject(subject);
        q.thumbnailImg(thumbnailRelPath);
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

    public void modify(Question question, String subject, String content) {
        question.setSubject(subject);
        question.setContent(content);
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

}