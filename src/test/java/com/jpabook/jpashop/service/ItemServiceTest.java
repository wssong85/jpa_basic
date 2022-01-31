package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.item.Book;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
@Transactional
public class ItemServiceTest {

  @Autowired
  EntityManager em;

  /**
   * 변경감지 확인
   */
  @Test
  public void updateTest() throws Exception {
    // given
    Book book = em.find(Book.class, 1L);

    // when
    book.setName("tttt");
    em.flush();

    Book book2 = em.find(Book.class, 1L);

    // then
    assertEquals("변경감지 확인", book.getName(), book2.getName());
  }

}