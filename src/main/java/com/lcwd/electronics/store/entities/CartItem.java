package com.lcwd.electronics.store.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.aot.generate.GeneratedTypeReference;

import javax.annotation.processing.Generated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "cart_items")
public class CartItem {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int cartItemId;

    @OneToOne //one direction CartItem me kitna product h
    @JoinColumn(name = "product_Id")
    private Product product;

    private int quantity;

    private int totalPrice;


    //mapping cart

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "cart_id")
    private Cart cart;
}
