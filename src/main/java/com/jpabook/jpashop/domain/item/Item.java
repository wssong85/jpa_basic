package com.jpabook.jpashop.domain.item;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.exception.NotEnoughStockException;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Inheritance(strategy = InheritanceType.SINGLE_TABLE)
@DiscriminatorColumn(name = "dtype")
@Getter @Setter
public abstract class Item {

  @Id
  @GeneratedValue
  @Column(name = "item_id")
  private Long id;

  private String name;
  private int price;
  private int stockQuantity;

//  @JsonIgnore
  @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
  private List<OrderItem> orderItems = new ArrayList<>();

//  @OneToMany(fetch = FetchType.LAZY, mappedBy = "item")
//  private List<CategoryItem> categoryItems = new ArrayList<>();

  @ManyToMany(mappedBy = "items")
  private List<Category> categories = new ArrayList<>();

  //==비지니스 로직==//
  /**
   * stock 증가
   */
  public void addStock(int quantity) {
    this.stockQuantity += quantity;
  }

  /**
   * stock 감소
   */
  public void removeStock(int quantity) {
    int restStock = this.stockQuantity - quantity;
    if (restStock < 0) {
      throw new NotEnoughStockException("need more stock");
    }
    this.stockQuantity = restStock;
  }

  /**
   * item 수정 비지니스 - 변경감지 (dirty checking)
   */
  public void changeItem(String name, int price, int stockQuantity) {
    this.price = price;
    this.name = name;
    this.stockQuantity = stockQuantity;
  }
}