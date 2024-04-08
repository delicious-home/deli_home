package com.start.deli_home.Member.Service;


import com.start.deli_home.Member.Entity.Member;
import com.start.deli_home.Member.Repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
}
