package com.jpabook.jpashop.test;

import com.jpabook.jpashop.domain.Member;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

@RunWith(SpringRunner.class)
@SpringBootTest
public class MemberRepositoryTest {

  @Autowired
  MemberRepositoryForTest memberRepositoryForTest;

  @Test
  @Transactional
  @Rollback(false)
  public void testMember() throws Exception {
    // given
    Member member = new Member();
    member.setName("memberA");

    // when
    Long saveId = memberRepositoryForTest.save(member);
    Member findMember = memberRepositoryForTest.find(saveId);

    // then
    Assertions.assertThat(findMember.getId()).isEqualTo(member.getId());
    Assertions.assertThat(findMember.getName()).isEqualTo(member.getName());
    Assertions.assertThat(findMember).isEqualTo(member);
    System.out.println("findMember === member " + (findMember == member));

  }

}