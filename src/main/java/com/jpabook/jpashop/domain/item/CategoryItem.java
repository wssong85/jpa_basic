package com.jpabook.jpashop.domain.item;

import javax.persistence.*;

//@Entity
//@Table(name = "category_item")
public class CategoryItem {

//  @Id
//  @GeneratedValue
//  @Column(name = "category_item_id")
  private Long id;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "category_id")
  private Category category;

//  @ManyToOne(fetch = FetchType.LAZY)
//  @JoinColumn(name = "item_id")
  private Item item;

}
