package com.github.toy.adapter.infrastructor.init;

import com.github.toy.domain.item.Item;
import javax.annotation.PostConstruct;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Profile("local")
@Component
@RequiredArgsConstructor
public class InitItem {

    private final InitItemService initItemService;

    @PostConstruct
    public void init() {
        initItemService.init();
    }

    @Component
    static class InitItemService {

        @PersistenceContext
        private EntityManager em;

        @Transactional
        public void init() {
            Item item1 = new Item("book1", 1000, 10);
            Item item2 = new Item("book2", 2000, 15);
            em.persist(item1);
            em.persist(item2);
        }
    }

}
