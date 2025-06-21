package com.order_service.order_service.service;


import com.order_service.order_service.dto.InventoryResponse;
import com.order_service.order_service.dto.OrderRequest;
import com.order_service.order_service.model.Order;
import com.order_service.order_service.model.OrderLineItems;
import com.order_service.order_service.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    WebClient webClient;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItemsList = orderRequest.getOrderLineItemsDtos()
                .stream()
                .map(olI ->
                        new OrderLineItems(olI.getId(),
                                olI.getSkuCode(),
                                olI.getPrice(),
                                olI.getQuantity())
                ).toList();

        order.setOrderLineItemsList(orderLineItemsList);

        // Call the inventory service to  check product in stack

        List<String> skuCodeList = order.getOrderLineItemsList()
                .stream()
                .map(OrderLineItems::getSkuCode)
                .toList();


        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8082/api/v1/stock",
                        uriBuilder -> uriBuilder.queryParam("skuCode",skuCodeList)
                                .build()
                )
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductInStock = Arrays.stream(inventoryResponses)
                .allMatch(InventoryResponse::isInStock);


        if (allProductInStock) {
            orderRepository.save(order);
        } else {
            throw new IllegalArgumentException("Order item are not in stock.");
        }
    }
}
