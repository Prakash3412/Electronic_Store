package com.lcwd.electronics.store.entities;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name = "orders")
public class Order {
    @Id
    private String oderId;

    //PENDING ,DELIVERED,DISPATCHING
    //ENUM
    private String oderStatus;

    //NOT PAID ,PAID
    //ENUM
    //BOOLEAN --> FALSE -> NOT PAID,TRUE->PAID
    private String paymentStatus;

    private int oderAmount;
    @Column(length = 1000)
    private String billingAddress;

    private String billingPhone;

    private String billingName;

    private Date oderedDate;

    private  Date deliveredDate;

    //user
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="user_id")
    private User user;

    @OneToMany(mappedBy = "order",fetch = FetchType.EAGER,cascade = CascadeType.ALL)
    private List<OrderItem> orderItem = new ArrayList<>();


}
