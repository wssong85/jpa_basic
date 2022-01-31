package com.jpabook.jpashop.service;

import com.jpabook.jpashop.domain.item.Item;
import com.jpabook.jpashop.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

  private final ItemRepository itemRepository;

  @Transactional
  public void saveItem(Item item) {
    itemRepository.save(item);
  }

  public List<Item> findItems() {
    return itemRepository.findAll();
  }

  public Item findOne(Long itemId) {
    return itemRepository.findOne(itemId);
  }

  /**
   * 변경감지 (dirty checking) update
   */
  @Transactional
  public Item updateItem(Long itemId, UpdateItemDto updateItemDto) {
    Item findItem = itemRepository.findOne(itemId);
    findItem.changeItem(updateItemDto.getName(), updateItemDto.getPrice(), updateItemDto.getStockQuantity());

//    Inheritance 변경감지
//    Book book = (Book) findItem;
//    book.setAuthor("시스템author");
//    book.setIsbn("시스템isbn");

    return findItem;
  }

//  @Transactional
//  public void updateItemByMerge(Long itemId, Book param) {
//    Item item = itemRepository.findOne(itemId);
//    item.setPrice(param.getPrice());
//    item.setName(param.getName());
//    item.setStockQuantity(param.getStockQuantity());
//  }
}
