package com.jpabook.jpashop;

import com.jpabook.jpashop.domain.*;
import com.jpabook.jpashop.domain.item.Book;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;

@Component
@RequiredArgsConstructor
public class InitDb {

  private final InitService initService;

//  @PostConstruct
  public void init() {
    initService.dbInit1();
    initService.dbInit2();
  }

  @Component
  @Transactional
  @RequiredArgsConstructor
  static class InitService {
    private final EntityManager em;
    public void dbInit1() {
      Member member = createMember("회원1", "서울", "1111", "11");
      em.persist(member);

      Book book1 = createBook("jpa_book1", 10000, 100);
      em.persist(book1);

      Book book2 = createBook("jpa_book2", 10000, 100);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 10000, 1);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 20000, 2);

      Delivery delivery = createDelivery(member);

      Order order = Order.createOrder(member, delivery, orderItem1, orderItem1, orderItem2);
      em.persist(order);
    }

    public void dbInit2() {
      Member member = createMember("회원2", "서울", "2222", "22");
      em.persist(member);

      Book book1 = createBook("spring_book1", 20000, 150);
      em.persist(book1);

      Book book2 = createBook("spring_book2", 40000, 150);
      em.persist(book2);

      OrderItem orderItem1 = OrderItem.createOrderItem(book1, 20000, 2);
      OrderItem orderItem2 = OrderItem.createOrderItem(book2, 40000, 4);

      Delivery delivery = createDelivery(member);

      Order order = Order.createOrder(member, delivery, orderItem1, orderItem1, orderItem2);
      em.persist(order);
    }

    private Delivery createDelivery(Member member) {
      Delivery delivery = new Delivery();
      delivery.setAddress(member.getAddress());
      delivery.setStatus(DeliveryStatus.READY);
      return delivery;
    }

    private Book createBook(String bookName, int price, int stockQuantity) {
      Book book1 = new Book();
      book1.setName(bookName);
      book1.setPrice(price);
      book1.setStockQuantity(stockQuantity);
      return book1;
    }

    private Member createMember(String name, String city, String street, String zipcode) {
      Member member = new Member();
      member.setName(name);
      member.setAddress(new Address(city, street, zipcode));
      return member;
    }
  }

}
