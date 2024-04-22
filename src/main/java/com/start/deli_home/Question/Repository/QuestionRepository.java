package com.start.deli_home.Question.Repository;

import com.start.deli_home.Question.Entity.Question;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

@Repository
public interface QuestionRepository extends JpaRepository<Question, Integer> {

    Question findBySubject(String subject);
    Page<Question> findAll(Pageable pageable);

    Page<Question> findByCategory(String category, Pageable pageable);

    Page<Question> findAll(Specification<Question> spec, Pageable pageable);

    Page<Question> findBySubjectContaining(String searchKeyword, Pageable pageable); // 제목에 포함된 키워드를 찾는 메서드

    @Query("select "
            + "distinct q "
            + "from Question q "
            + "left outer join Member u1 on q.author=u1 "
            + "left outer join Review a on a.question=q "
            + "left outer join Member u2 on a.author=u2 "
            + "where "
            + "   q.subject like %:kw% "
            + "   or q.content like %:kw% "
            + "   or u1.username like %:kw% "
            + "   or a.content like %:kw% "
            + "   or u2.username like %:kw% ")
    Page<Question> findAllByKeyword(@Param("kw") String kw, Pageable pageable);

}