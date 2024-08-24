package com.lcwd.electronics.store.dtos;

import com.lcwd.electronics.store.entities.Order;
import com.lcwd.electronics.store.entities.Product;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class OrderItemDto {
    private int OrderItemId;
    private int quantity;
    private int totalPrice;
    private ProductDto product;

}
