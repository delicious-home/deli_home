package com.start.deli_home.Member.Repository;

import com.start.deli_home.Member.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Integer> {

    Optional<Member> findByusername(String username);
    Optional<Member> findById(Long id);
    Optional<Member> findByUsername(String username);

    Optional<Member> findByEmail(String email);
}
