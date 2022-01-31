package com.jpabook.jpashop.domain.item;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
public class Category {

  @Id
  @GeneratedValue
  @Column(name = "category_id")
  private Long id;

  private String name;

  // 자식(N명, 맨 윗줄 class 입장)입장에서 부모(1명)
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "parent_id")
  private Category parent;

//  * column 기반 관계 계층
//  @Column(name = "parent_id")
//  private Long parentId;
//  @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
//  @JoinColumn(name = "parent_id")
//  private List<Category> child = new ArrayList<>();
//  * column 기반 관계 계층

  // 부모(1명, 맨 윗줄 class 입장)입장에서 자식(N명)
  @OneToMany(mappedBy = "parent")
  private List<Category> child = new ArrayList<>();

//  @OneToMany(fetch = FetchType.LAZY, mappedBy = "category")
//  private List<CategoryItem> categoryItem;

  @ManyToMany
  @JoinTable(name = "category_item",
          joinColumns = @JoinColumn(name = "category_id"),
          inverseJoinColumns = @JoinColumn(name = "item_id"))
  private List<Item> items = new ArrayList<>();


  //==연관관계 편의 메서드==//

  public void addChildCategory(Category child) {
    this.child.add(child);
    child.setParent(this);
  }

}