package com.jpabook.jpashop.repository.order.query;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class OrderItemQueryDto {

  @JsonIgnore
  private Long orderId;
  private String itemName;
  private int orderPrice;
  private int orderStockQuentity;

  public OrderItemQueryDto(Long orderId, String itemName, int orderPrice, int orderStockQuentity) {
    this.orderId = orderId;
    this.itemName = itemName;
    this.orderPrice = orderPrice;
    this.orderStockQuentity = orderStockQuentity;
  }
}
