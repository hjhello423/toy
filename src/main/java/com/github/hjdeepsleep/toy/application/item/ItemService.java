package com.github.hjdeepsleep.toy.application.item;

import com.github.hjdeepsleep.toy.adapter.infrastructor.repository.jqpl_test.item.ItemJpqlRepository;
import com.github.hjdeepsleep.toy.domain.item.Book;
import com.github.hjdeepsleep.toy.domain.item.Item;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class ItemService {

    private final ItemJpqlRepository itemJpqlRepository;

    @Transactional
    public void saveItem(Item item) {
        itemJpqlRepository.save(item);
    }

    @Transactional
    public void updateItem(Long itemId, Book param) {
        Item findItem = itemJpqlRepository.findOne(itemId);
        findItem.setPrice(param.getPrice());
        findItem.setName(param.getName());
        findItem.setStockQuantity(param.getStockQuantity());
    }

    public List<Item> findItems() {
        return itemJpqlRepository.findAll();
    }

    public Item findOne(Long id) {
        return itemJpqlRepository.findOne(id);
    }
}
