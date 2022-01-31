package com.jpabook.jpashop.test;

import com.jpabook.jpashop.domain.Member;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Repository
public class MemberRepositoryForTest {

  @PersistenceContext
  private EntityManager entityManager;

  public Long save(Member member) {
    entityManager.persist(member);
    return member.getId();
  }

  public Member find(Long id) {
    return entityManager.find(Member.class, id);
  }
}
