package com.start.deli_home;

import com.start.deli_home.Question.Service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.start.deli_home.Question.Service.QuestionService;

@SpringBootTest
class DeliHomeApplicationTests {

	@Autowired
	private QuestionService questionService;

	@Test
	void contextLoads() {
		// 테스트용 가상의 게시글 데이터 생성
		for (int i = 1; i <= 300; i++) {
			String subject = String.format("테스트 데이터입니다:[%03d]", i);
			String content = "내용무";
			this.questionService.create(subject, content);
		}
	}
}
