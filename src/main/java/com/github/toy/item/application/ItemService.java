package com.github.toy.item.application;

import com.github.toy.item.infrastructure.jpql_test.ItemJpqlRepository;
import com.github.toy.item.domain.Book;
import com.github.toy.item.domain.Item;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
