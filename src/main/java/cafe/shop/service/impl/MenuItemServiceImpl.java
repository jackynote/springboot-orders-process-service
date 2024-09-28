package cafe.shop.service.impl;

import cafe.shop.model.dto.MenuItemDto;
import cafe.shop.model.entities.Franchise;
import cafe.shop.model.entities.MenuItem;
import cafe.shop.repository.FranchiseRepository;
import cafe.shop.repository.MenuItemRepository;
import cafe.shop.service.BaseService;
import cafe.shop.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class MenuItemServiceImpl extends BaseService implements MenuItemService {

    @Autowired
    private MenuItemRepository menuItemRepository;

    @Autowired
    private FranchiseRepository franchiseRepository;

    @Override
    public List<MenuItemDto> createMenuItem(UUID franchiseId, List<MenuItemDto> menuItemDtos) {
        Franchise franchise = franchiseRepository.findById(franchiseId)
                .orElseThrow(() -> new RuntimeException("Franchise not found"));

        List<MenuItem> menus = menuItemDtos.stream().map(dto -> {
            MenuItem menu = new MenuItem();
            menu.setFranchise(franchise);
            menu.setName(dto.getName());
            menu.setPrice(dto.getPrice());
            menu.setCurrency(dto.getCurrency());
            menu.setAvailable(dto.isAvailable());
            return menu;
        }).collect(Collectors.toList());

        menus = menuItemRepository.saveAll(menus);
        return menus.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    @Override
    public MenuItemDto updateMenuItem(UUID franchiseId, UUID menuId, MenuItemDto menuItemDto) {
        MenuItem menu = menuItemRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found"));

        if (!menu.getFranchise().getId().equals(franchiseId)) {
            throw new RuntimeException("Franchise mismatch");
        }

        menu.setName(menuItemDto.getName());
        menu.setPrice(menuItemDto.getPrice());
        menu.setCurrency(menuItemDto.getCurrency());
        menu.setAvailable(menuItemDto.isAvailable());

        menu = menuItemRepository.save(menu);
        return convertToDto(menu);
    }

    @Override
    public void deleteMenuItem(UUID franchiseId, UUID menuId) {
        MenuItem menu = menuItemRepository.findById(menuId).orElseThrow(() -> new RuntimeException("Menu not found"));

        if (!menu.getFranchise().getId().equals(franchiseId)) {
            throw new RuntimeException("Franchise mismatch");
        }

        menuItemRepository.delete(menu);
    }

    public List<MenuItemDto> getMenuItems(UUID franchiseId) {
        return menuItemRepository.findByFranchiseId(franchiseId).stream()
                .map(this::convertToDto)
                .toList();
    }

    private MenuItemDto convertToDto(MenuItem menuItem) {
        MenuItemDto dto = new MenuItemDto();
        dto.setId(menuItem.getId().toString());
        dto.setName(menuItem.getName());
        dto.setPrice(menuItem.getPrice());
        dto.setCurrency(menuItem.getCurrency());
        dto.setAvailable(menuItem.isAvailable());
        return dto;
    }


}
