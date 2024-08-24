package com.lcwd.electronics.store.dtos;

import com.lcwd.electronics.store.entities.OrderItem;
import com.lcwd.electronics.store.entities.User;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class OrderDto {
    private String oderId;
    private String oderStatus="PENDING";
    private String paymentStatus="NOTPAID";
    private String oderAmount;
    private String billingAddress;
    private String billingPhone;
    private String billingName;
    private Date oderedDate = new Date();
    private  Date deliveredDate;
  //  private UserDto user;
    private List<OrderItemDto> orderItem = new ArrayList<>();

}
