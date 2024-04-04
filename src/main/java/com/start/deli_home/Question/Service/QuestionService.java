package com.start.deli_home.Question.Service;

import com.start.deli_home.Question.DataNotFoundException.DataNotFoundException;
import com.start.deli_home.Question.Entity.Question;
import com.start.deli_home.Question.Repository.QuestionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class QuestionService {

    private final QuestionRepository questionRepository;

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

    public void create(String subject, String content,String category) {

        Question q = new Question();
        q.setContent(content);
        q.setSubject(subject);
        q.setCategory(category);
        q.setCreateDate(LocalDateTime.now());
        this.questionRepository.save(q);
    }

    public Page<Question> getList(int page, String category) {
        Pageable pageable = PageRequest.of(page, 10, Sort.by("createDate").descending());
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
}