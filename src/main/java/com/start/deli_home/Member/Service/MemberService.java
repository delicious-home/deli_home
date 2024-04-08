package com.start.deli_home.Member.Service;


import com.start.deli_home.Email.EmailService;
import com.start.deli_home.Member.Entity.Member;
import com.start.deli_home.Member.Repository.MemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final EmailService emailService;

    public Member create(String username, String email, String password) {
        Member user = new Member();
        user.setUsername(username);
        user.setEmail(email);
        user.setPassword(passwordEncoder.encode(password));
        this.memberRepository.save(user);
        return user;
    }
    public Member findByEmail(String email) {
        Optional<Member> member = this.memberRepository.findByEmail(email);
        if (member.isEmpty()) {
            return null;
        }
        return member.get();
    }
    @Transactional
    public void modifyPassword(String email) throws EmailService.EmailException {
        SecureRandom random = new SecureRandom();
        String tempPassword = generateRandomPassword(random, 8);
        Member member = this.memberRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("해당 이메일의 유저가 없습니다."));
        Member newMember = new Member();
            newMember.setPassword(passwordEncoder.encode(tempPassword));

        this.memberRepository.save(newMember);
        emailService.sendSimpleMessage(email, tempPassword);
    }

    public static String generateRandomPassword(SecureRandom random, int length) {
        final String chars = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789!@#$%^&*()-_=+";

        StringBuilder password = new StringBuilder();
        for (int i = 0; i < length; i++) {
            password.append(chars.charAt(random.nextInt(chars.length())));
        }
        return password.toString();
    }
}
