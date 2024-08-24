package com.lcwd.electronics.store.services.imple;

import com.lcwd.electronics.store.dtos.CreateOrderRequest;
import com.lcwd.electronics.store.dtos.OrderDto;
import com.lcwd.electronics.store.dtos.PageableResponse;
import com.lcwd.electronics.store.entities.*;
import com.lcwd.electronics.store.exception.BadApiRequest;
import com.lcwd.electronics.store.exception.ResourceNotFoundException;
import com.lcwd.electronics.store.helper.Helper;
import com.lcwd.electronics.store.repositories.CartRepository;
import com.lcwd.electronics.store.repositories.OrderRepository;
import com.lcwd.electronics.store.repositories.UserRepository;
import com.lcwd.electronics.store.services.OrderService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private CartRepository cartRepository;
    @Override
    public OrderDto createOrder(CreateOrderRequest orderDto) {
        String userId=orderDto.getUserId();
        String cartId = orderDto.getCartId();

        //fetch user
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found given id"));

        //fetch cart
        Cart cart = cartRepository.findById(cartId).orElseThrow(() -> new ResourceNotFoundException("cart not found given id"));

        //get cartItem from the cart
        List<CartItem> cartItems = cart.getItems();

        if(cartItems.size()<=0){
            throw new BadApiRequest("invalid number of items in cart!!");

        }

        Order order = Order.builder()
                .billingPhone(orderDto.getBillingPhone())
                .billingName(orderDto.getBillingName())
                .billingAddress(orderDto.getBillingAddress())
                .oderedDate(new Date())
                .deliveredDate(null)
                .paymentStatus(orderDto.getPaymentStatus())
                .oderStatus(orderDto.getOderStatus())
                .oderId(UUID.randomUUID().toString())
                .user(user)
                .build();

        //cartItems,amount did not set

        AtomicReference<Integer> orderAmount = new AtomicReference<>(0);

        List<OrderItem> orderItems = cartItems.stream().map(cartItem -> {

            //cartItem convert into the  orderItem

            OrderItem orderItem = OrderItem.builder()
                    .quantity(cartItem.getQuantity())
                    .product(cartItem.getProduct())
                    .totalPrice(cartItem.getQuantity() * cartItem.getProduct().getDiscountedPrice())
                    .order(order)
                    .build();

            //set amount of order
            orderAmount.set(orderAmount.get() + orderItem.getTotalPrice());

            return orderItem;

        }).collect(Collectors.toList());

        order.setOrderItem(orderItems);
        order.setOderAmount(orderAmount.get());

        //cart clear
        cart.getItems().clear();
        //now save the cart and order

        cartRepository.save(cart);

        Order savedOrder = orderRepository.save(order);

        return modelMapper.map(savedOrder,OrderDto.class);
    }

    @Override
    public void removeOrder(String orderId) {

        //fetch oderId
        Order order = orderRepository.findById(orderId).orElseThrow(() -> new ResourceNotFoundException("order ia not found!!"));
        orderRepository.delete(order); //order delete to uske orderItem bhi delete hoga kyunki we are used cascadtType.ALL

    }

    @Override
    public List<OrderDto> getOrdersOfUser(String userId) {
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("user not found!!"));
        List<Order> orders = orderRepository.findByUser(user);
        List<OrderDto> orderDtos = orders.stream().map(order -> modelMapper.map(order, OrderDto.class)).collect(Collectors.toList());
        return orderDtos;
    }

    @Override
    public PageableResponse<OrderDto> getOrders(int pageNumber, int pageSize, String sortBy, String sortDir) {

        Sort sort =(sortDir.equalsIgnoreCase("desc"))?(Sort.by(sortBy).descending()):(Sort.by(sortBy).ascending());

        Pageable pageable= PageRequest.of(pageNumber,pageSize,sort);

        Page<Order> page = orderRepository.findAll(pageable);
        return Helper.getPageableResponse(page,OrderDto.class);
    }
}
