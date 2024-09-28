package cafe.shop.controller;

import cafe.shop.model.dto.MenuItemDto;
import cafe.shop.service.MenuItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;


@RestController
@RequestMapping(value = "/api/v1/franchises/{franchiseId}/menu")
public class MenuItemController {

    @Autowired
    private MenuItemService menuItemService;

    @PostMapping
    public ResponseEntity<List<MenuItemDto>> createMenuItem(
            @PathVariable UUID franchiseId,
            @RequestBody List<MenuItemDto> menuItemDtos) {
        List<MenuItemDto> createdMenuItem = menuItemService.createMenuItem(franchiseId, menuItemDtos);
        return new ResponseEntity<>(createdMenuItem, HttpStatus.CREATED);
    }

    @PutMapping("/{menuItemId}")
    public ResponseEntity<MenuItemDto> updateMenuItem(
            @PathVariable UUID franchiseId,
            @PathVariable UUID menuItemId,
            @RequestBody MenuItemDto menuItemDto) {
        MenuItemDto updatedMenuItem = menuItemService.updateMenuItem(franchiseId, menuItemId, menuItemDto);
        return new ResponseEntity<>(updatedMenuItem, HttpStatus.OK);
    }

    @DeleteMapping("/{menuItemId}")
    public ResponseEntity<Void> deleteMenuItem(
            @PathVariable UUID franchiseId,
            @PathVariable UUID menuItemId) {
        menuItemService.deleteMenuItem(franchiseId, menuItemId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    public ResponseEntity<List<MenuItemDto>> getMenuItems(@PathVariable UUID franchiseId) {
        List<MenuItemDto> menuItems = menuItemService.getMenuItems(franchiseId);
        return new ResponseEntity<>(menuItems, HttpStatus.OK);
    }

}
