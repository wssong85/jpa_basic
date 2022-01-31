package com.jpabook.jpashop.api;

import com.jpabook.jpashop.domain.Address;
import com.jpabook.jpashop.domain.Order;
import com.jpabook.jpashop.domain.OrderItem;
import com.jpabook.jpashop.domain.OrderStatus;
import com.jpabook.jpashop.repository.OrderRepository;
import com.jpabook.jpashop.repository.OrderSearch;
import com.jpabook.jpashop.repository.order.query.OrderFlatDto;
import com.jpabook.jpashop.repository.order.query.OrderQueryDto;
import com.jpabook.jpashop.repository.order.query.OrderQueryRepository;
import com.jpabook.jpashop.service.OrderService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

import static java.util.stream.Collectors.toList;

@RestController
@RequiredArgsConstructor
public class OrderApiController {

  private final OrderRepository orderRepository;

  private final OrderQueryRepository orderQueryRepository;
//  private final OrderService orderService;

  @GetMapping("/api/v1/orders")
  public List<Order> orderV1() {
    List<Order> result = orderRepository.findAllByString(new OrderSearch());
    for (Order order : result) {
      order.getMember().getName();
      order.getDelivery();
      List<OrderItem> orderItems = order.getOrderItems();
      orderItems.forEach(c2 -> c2.getItem().getName());
    }
    return result;
  }

  @GetMapping("/api/v2/orders")
  public List<OrderDto> orderV2() {
    List<Order> orders = orderRepository.findAllByString(new OrderSearch());
    List<OrderDto> result = orders.stream()
            .map(o -> new OrderDto(o))
            .collect(toList());
    return result;
  }

  @GetMapping("/api/v3/orders")
  public List<OrderDto> orderV3() {
    List<Order> orders = orderRepository.findAllWithItems();
    List<OrderDto> result = orders.stream()
            .map(o -> new OrderDto(o))
            .collect(toList());
    return result;
  }

  @GetMapping("/api/v3.1/orders")
  public List<OrderDto> orderV3_page(@RequestParam(value = "offset", defaultValue = "0") int offset,
                                     @RequestParam(value = "limit", defaultValue = "100") int limit) {
    List<Order> orders = orderRepository.findAllWithMemberDelivery(offset, limit);

    List<OrderDto> result = orders.stream()
            .map(o -> new OrderDto(o))
            .collect(toList());
    return result;
  }

  @GetMapping("/api/v4/orders")
  public List<OrderQueryDto> orderV4() {
    return orderQueryRepository.findOrderQueryDtos();
  }

  @GetMapping("/api/v5/orders")
  public List<OrderQueryDto> orderV5() {
    return orderQueryRepository.findAllByDto_optimization();
  }

  @GetMapping("/api/v6/orders")
  public List<OrderFlatDto> orderV6() {
    return orderQueryRepository.findAllByDto_flat();
  }

  @Getter
  static class OrderDto {
    private Long orderId;

    private String name;
    private LocalDateTime orderDate;
    private OrderStatus status;
    private Address address;
    private List<OrderItemDto> orderItems;

    public OrderDto(Order order) {
      orderId = order.getId();
      name = order.getMember().getName();
      orderDate = order.getOrderDate();
      status = order.getStatus();
      address = order.getDelivery().getAddress();
//      o.getOrderItems().stream().forEach(c -> c.getItem().getName());
      orderItems = order.getOrderItems().stream().map(OrderItemDto::new).collect(toList());
    }
  }

  @Getter
  static class OrderItemDto {

    private String itemName;
    private int orderPrice;
    private int count;

    public OrderItemDto(OrderItem orderItem) {
      itemName = orderItem.getItem().getName();
      orderPrice = orderItem.getOrderPrice();
      count = orderItem.getCount();
    }
  }
}
