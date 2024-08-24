package com.lcwd.electronics.store.dtos;

import com.lcwd.electronics.store.entities.Cart;
import com.lcwd.electronics.store.entities.Product;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CartItemDto {
    private int cartItemId;

    private ProductDto productDto;

    private int quantity;

    private int totalPrice;
}
