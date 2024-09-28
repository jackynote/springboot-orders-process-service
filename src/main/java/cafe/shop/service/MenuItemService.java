package cafe.shop.service;

import cafe.shop.model.dto.MenuItemDto;

import java.util.List;
import java.util.UUID;

public interface MenuItemService {

    List<MenuItemDto> createMenuItem(UUID franchiseId, List<MenuItemDto> menuItemDtos);

    MenuItemDto updateMenuItem(UUID franchiseId, UUID menuId, MenuItemDto menuItemDto);

    void deleteMenuItem(UUID franchiseId, UUID menuId);

    List<MenuItemDto> getMenuItems(UUID franchiseId);
}
