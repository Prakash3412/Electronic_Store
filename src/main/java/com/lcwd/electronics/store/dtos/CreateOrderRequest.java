package com.lcwd.electronics.store.dtos;

import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class CreateOrderRequest {

    @NotBlank(message = "cart id is required!!")
    private String cartId;

    @NotBlank(message = "user id is required!!")
    private String userId;

    private String oderStatus="PENDING";
    private String paymentStatus="NOTPAID";

    @NotBlank(message = "billing address is required!!")
    private String billingAddress;

    @NotBlank(message = "phone number is required!!")
    private String billingPhone;

    @NotBlank(message = "billing name is required!!")
    private String billingName;

}
