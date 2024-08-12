package com.sunbeam.controller;

import com.sunbeam.DTO.WishListItemDTO;
import com.sunbeam.services.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/wishlist")
public class WishListController {
    @Autowired
    private WishListService wishListService;

    @GetMapping("/{userId}")
    public ResponseEntity<List<WishListItemDTO>> getWishlistItems(@PathVariable Long userId) {
        List<WishListItemDTO> items = wishListService.getWishlistItems(userId);
        return ResponseEntity.ok(items);
    }

    @PostMapping("/{userId}/add/{propertyId}")
    public ResponseEntity<WishListItemDTO> addItemToWishlist(@PathVariable Long userId, @PathVariable Long propertyId) {
        WishListItemDTO item = wishListService.addItemToWishlist(userId, propertyId);
        return ResponseEntity.ok(item);
    }

    @DeleteMapping("/{userId}/remove/{itemId}")
    public ResponseEntity<Void> removeItemFromWishlist(@PathVariable Long userId, @PathVariable Long itemId) {
        wishListService.removeItemFromWishlist(userId, itemId);
        return ResponseEntity.noContent().build();
    }
}
