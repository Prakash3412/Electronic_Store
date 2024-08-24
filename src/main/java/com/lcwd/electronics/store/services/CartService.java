package com.lcwd.electronics.store.services;

import com.lcwd.electronics.store.dtos.AddItemToCartRequest;
import com.lcwd.electronics.store.dtos.CartDto;
import org.springframework.stereotype.Service;


public interface CartService {

    //add items to cart
    //case 1->cart for user not available: we will create the cart and then add the items
    //case 2->cart available add the items to cart

    CartDto addItemToCart(String userId, AddItemToCartRequest request);

    //remove items from the cart
    void removeItemFromCart(String userId, int cartItem );

    //clear cart
    void clearCart(String userId);

    CartDto getCartByUser(String userId);


}
