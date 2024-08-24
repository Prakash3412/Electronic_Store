package com.lcwd.electronics.store.dtos;

import jakarta.persistence.Column;
import lombok.*;

import java.util.Date;
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class ProductDto {
    private String productId;
    private String title;
    private String description;
    private int price;
    private int discountedPrice;
    private int quantity;
    private Date addedDate;
    private boolean live;
    private boolean stock;
    private String productImageName;

    //create product with category dto ke andar dto hi use hogs
   // private CategoryDto category;
}
