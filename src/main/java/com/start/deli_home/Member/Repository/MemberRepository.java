package com.start.deli_home.Member.Repository;

import com.start.deli_home.Member.Entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Integer> {
}
