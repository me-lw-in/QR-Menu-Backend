package com.melwin.qrmenu.service.menu;

import com.melwin.qrmenu.dto.menu.create.ItemBlockDto;
import com.melwin.qrmenu.entity.Item;
import com.melwin.qrmenu.entity.User;
import com.melwin.qrmenu.repository.account.UserRepository;
import com.melwin.qrmenu.repository.menu.ItemRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@AllArgsConstructor
@Service
public class ItemService {

    private final ItemRepository itemRepository;
    private final UserRepository userRepository;

    public Optional<Item> createItem(ItemBlockDto item, Long ownerId) {
        Item newItemEntity = new Item();
        newItemEntity.setName(item.getName().trim());
        System.out.println("Item name is inside createItem: " + item.getName().trim());
        User user = userRepository.findUserById(ownerId);
        newItemEntity.setCreatedBy(user);

        newItemEntity.setIsDefault(false);
        newItemEntity.setIsVeg(item.getIsVeg());

        itemRepository.save(newItemEntity);
        return Optional.of(newItemEntity);
    }
}
