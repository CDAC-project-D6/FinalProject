package com.sunbeam.services;

import com.sunbeam.DTO.WishListItemDTO;
import com.sunbeam.entities.Property;

import com.sunbeam.entities.User;
import com.sunbeam.entities.WishList;
import com.sunbeam.entities.WishListItem;
import com.sunbeam.DAO.PropertyDao;

import com.sunbeam.DAO.UserDao;
import com.sunbeam.DAO.WishListItemDao;
import com.sunbeam.DAO.WishListDao;
import com.sunbeam.services.WishListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class WishListServiceImpl implements WishListService {
    @Autowired
    private WishListDao wishListDao;

    @Autowired
    private WishListItemDao wishListItemDao;

    @Autowired
    private PropertyDao propertyDao;

    @Autowired
    private UserDao userDao;
    
//    @Autowired
//    private TenantDao tenantDao; // Use TenantDao instead of UserDao

    @Override
    public WishList getWishlistByUserId(Long userId) {
        return wishListDao.findByUserId(userId);
    }

    @Override
    public WishListItemDTO addItemToWishlist(Long userId, Long propertyId) {
        WishList wishlist = getWishlistByUserId(userId);
        if (wishlist == null) {
            wishlist = new WishList();
            User user = userDao.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
            wishlist.setUser(user);
            wishListDao.save(wishlist);
        }

        Property property = propertyDao.findById(propertyId).orElseThrow(() -> new RuntimeException("Property not found"));
        WishListItem item = new WishListItem();
        item.setWishlist(wishlist);
        item.setProperty(property);
        wishListItemDao.save(item);
        
        wishlist.addItem(item); // Update the wishlist with the new item
        wishListDao.save(wishlist); // Save the wishlist with the updated items

        WishListItemDTO itemDTO = new WishListItemDTO();
        itemDTO.setId(item.getId());
        itemDTO.setWishlistId(wishlist.getId());
        itemDTO.setPropertyId(property.getId());

        return itemDTO;
    }


    @Override
    public void removeItemFromWishlist(Long userId, Long itemId) {
        WishList wishlist = getWishlistByUserId(userId);
        if (wishlist != null) {
            WishListItem item = wishListItemDao.findById(itemId).orElseThrow(() -> new RuntimeException("Item not found"));
            wishlist.removeItem(item); // Remove the item from the wishlist
            wishListItemDao.delete(item); // Delete the item from the database
            wishListDao.save(wishlist); // Save the wishlist with the updated items
        }
    }


    @Override
    public List<WishListItemDTO> getWishlistItems(Long userId) {
        WishList wishlist = getWishlistByUserId(userId);
        if (wishlist != null) {
            List<WishListItemDTO> itemDTOs = new ArrayList<>();
            for (WishListItem item : wishlist.getItems()) {
                WishListItemDTO itemDTO = new WishListItemDTO();
                itemDTO.setId(item.getId());
                itemDTO.setWishlistId(wishlist.getId());
                itemDTO.setPropertyId(item.getProperty().getId());
                itemDTOs.add(itemDTO);
            }
            return itemDTOs;
        }
        return new ArrayList<>();
    }

}
