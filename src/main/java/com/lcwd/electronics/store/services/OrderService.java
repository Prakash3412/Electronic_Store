package com.lcwd.electronics.store.services;

import com.lcwd.electronics.store.dtos.OrderDto;
import com.lcwd.electronics.store.dtos.PageableResponse;
import com.lcwd.electronics.store.dtos.CreateOrderRequest;

import java.util.List;

public interface OrderService {

    //create order
    OrderDto createOrder(CreateOrderRequest orderDto);

    //remove order
    void removeOrder(String orderId);

    //get orders of user
    List<OrderDto> getOrdersOfUser(String userId);

    //get orders

    PageableResponse<OrderDto> getOrders(int pageNumber,int pageSize,String sortBy,String sortDir);

}
