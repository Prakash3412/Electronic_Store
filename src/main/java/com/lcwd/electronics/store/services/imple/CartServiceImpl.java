package com.lcwd.electronics.store.services.imple;

import com.lcwd.electronics.store.dtos.AddItemToCartRequest;
import com.lcwd.electronics.store.dtos.CartDto;
import com.lcwd.electronics.store.entities.Cart;
import com.lcwd.electronics.store.entities.CartItem;
import com.lcwd.electronics.store.entities.Product;
import com.lcwd.electronics.store.entities.User;
import com.lcwd.electronics.store.exception.BadApiRequest;
import com.lcwd.electronics.store.exception.ResourceNotFoundException;
import com.lcwd.electronics.store.repositories.CartItemRepository;
import com.lcwd.electronics.store.repositories.CartRepository;
import com.lcwd.electronics.store.repositories.ProductRepository;
import com.lcwd.electronics.store.repositories.UserRepository;
import com.lcwd.electronics.store.services.CartService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class CartServiceImpl implements CartService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private CartRepository cartRepository;
    @Autowired
    private ModelMapper mapper;
    @Autowired
    private CartItemRepository cartItemRepository;
    @Override
    public CartDto addItemToCart(String userId, AddItemToCartRequest request) {
        int quantity = request.getQuantity();
        String productId = request.getProductId();

        if(quantity<=0){
            throw new BadApiRequest("Request quantity is not valid");
        }

        //fetch the product first then add into then cart
        Product product = productRepository.findById(productId).orElseThrow(() -> new ResourceNotFoundException("product not found in the database!!"));

        //fetch the user from the db

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found given id!!"));

        Cart cart = new Cart();

        try{
            cart=cartRepository.findByUser(user).get();
        }
         catch (NoSuchElementException e){
            //cart= new Cart();
            cart.setCartId(UUID.randomUUID().toString());
            cart.setCreatedAt(new Date());
         }

        //perform cart operation
        //if cart items already present; then update
        AtomicReference<Boolean> updated= new AtomicReference<>(false);
        List<CartItem> items = cart.getItems();
        items = items.stream().map(item -> {

            if (item.getProduct().getProductId().equals(productId)) {
                //item already present in cart
                item.setQuantity(quantity);
                item.setTotalPrice(quantity*product.getDiscountedPrice());
                updated.set(true);


            }
            return item;
        }).collect(Collectors.toList());

      //k  cart.setItems(updatedItems);

        //we have to create items
       if(!updated.get()){
           CartItem cartItem = CartItem.builder()
                   .quantity(quantity)
                   .totalPrice(quantity * product.getDiscountedPrice())
                   .cart(cart)
                   .product(product)
                   .build();

           cart.getItems().add(cartItem);
       }

        cart.setUser(user);

        Cart updatedCart = cartRepository.save(cart);
        return mapper.map(updatedCart,CartDto.class);
    }

    @Override
    public void removeItemFromCart(String userId, int cartItem) {
        //conditions
        CartItem cartItem1 = cartItemRepository.findById(cartItem).orElseThrow(() -> new ResourceNotFoundException("cart items not found in db!!"));
        cartItemRepository.delete(cartItem1);

    }

    @Override
    public void clearCart(String userId) {
        //fetch the user from the db

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found given id!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart of given not found !!"));
        cart.getItems().clear();
        cartRepository.save(cart);


    }

    @Override
    public CartDto getCartByUser(String userId) {

        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found given id!!"));
        Cart cart = cartRepository.findByUser(user).orElseThrow(() -> new ResourceNotFoundException("cart of given not found !!"));
        return mapper.map(cart,CartDto.class);
    }
}
