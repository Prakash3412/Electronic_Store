package com.lcwd.electronics.store.controllers;

import com.lcwd.electronics.store.dtos.ApiResponseMessage;
import com.lcwd.electronics.store.dtos.CreateOrderRequest;
import com.lcwd.electronics.store.dtos.OrderDto;
import com.lcwd.electronics.store.dtos.PageableResponse;
import com.lcwd.electronics.store.services.OrderService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orders")
public class OrderController {


    @Autowired
    private OrderService orderService;

    //create order
    @PostMapping
    public ResponseEntity<OrderDto> createOrder( @Valid @RequestBody CreateOrderRequest  request)
    {
        OrderDto order = orderService.createOrder(request);
        return new ResponseEntity<>(order, HttpStatus.CREATED);
    }

    //remove order

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseMessage> removeOrder(@PathVariable String orderId){

        orderService.removeOrder(orderId);
        ApiResponseMessage responseMessage = ApiResponseMessage.builder()
                .status(HttpStatus.OK)
                .success(true)
                .message("order is removed!!")
                .build();
        return new ResponseEntity<>(responseMessage,HttpStatus.OK);
    }

    //get orders of the user

    @GetMapping("/users/{userId}")
    public ResponseEntity<List<OrderDto>> getOrdersOfUsers(@PathVariable String userId)
    {
        List<OrderDto> ordersOfUser = orderService.getOrdersOfUser(userId);
        return new ResponseEntity<>(ordersOfUser,HttpStatus.OK);
    }
    //get all orders from the user

    @GetMapping
    public ResponseEntity<PageableResponse<OrderDto>> getOrders
            (
             @RequestParam(value = "pageNumber",defaultValue = "0",required = false) int pageNumber,
             @RequestParam(value = "pageSize",defaultValue = "10",required = false) int pageSize,
             @RequestParam(value = "sortBy",defaultValue = "oderedDate",required = false) String sortBy,
             @RequestParam(value = "sortDir",defaultValue = "desc",required = false) String sortDir
                                                                             )
    {
        PageableResponse<OrderDto> orders = orderService.getOrders(pageNumber, pageSize, sortBy, sortDir);
        return new ResponseEntity<>(orders,HttpStatus.OK);
    }
}
