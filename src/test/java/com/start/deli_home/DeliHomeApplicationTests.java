package com.start.deli_home;

import com.start.deli_home.Member.Entity.Member;
import com.start.deli_home.Member.Repository.MemberRepository;
import com.start.deli_home.Member.Service.MemberService;
import com.start.deli_home.Question.Service.QuestionService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import com.start.deli_home.Question.Service.QuestionService;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class DeliHomeApplicationTests {

	@Autowired
	private QuestionService questionService;
	@Autowired
	private MemberService memberService;
	@Autowired
	private MemberRepository memberRepository;

	@Autowired
	private PasswordEncoder passwordEncoder;
	@Test
	void createAdmin() {
		Member member = new Member();
		member.setUsername("admin");
		member.setPassword(passwordEncoder.encode("1234"));
		member.setEmail("admin@123");
		this.memberRepository.save(member);
	}
}
