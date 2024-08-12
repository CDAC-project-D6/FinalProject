package com.sunbeam.services;

import com.sunbeam.DTO.WishListItemDTO;
import com.sunbeam.entities.WishList;

import java.util.List;

public interface WishListService {
    WishList getWishlistByUserId(Long userId);
    WishListItemDTO addItemToWishlist(Long userId, Long propertyId);
    void removeItemFromWishlist(Long userId, Long itemId);
    List<WishListItemDTO> getWishlistItems(Long userId);
}

