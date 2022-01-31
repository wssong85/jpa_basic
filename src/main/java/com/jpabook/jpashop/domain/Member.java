package com.jpabook.jpashop.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class Member {

  @Id @GeneratedValue
  @Column(name = "member_id")
  private Long id;

  @NotEmpty
  private String name;

//  @JsonIgnore
  @Embedded
  private Address address;

// JsonIgnore 보다는, dto 로 왠만하면 해결하여 프레젠테이션 계층과 연결을 최대한 끊자
//  @JsonIgnore
  @OneToMany(mappedBy = "member")
  private List<Order> orders = new ArrayList<>();
}
