package com.jpabook.jpashop.repository;

import com.jpabook.jpashop.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MemberRepository extends JpaRepository<Member, Long> {

  // name을 찾아서 조회해줌
  // select m from Member m where m.name = ?
  List<Member> findByName(String name);
}
