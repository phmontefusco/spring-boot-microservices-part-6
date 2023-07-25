package com.programmingtechie.orderservice.service;

import com.programmingtechie.orderservice.client.InventoryClient;
import com.programmingtechie.orderservice.dto.InventoryResponse;
import com.programmingtechie.orderservice.dto.OrderLineItemsDto;
import com.programmingtechie.orderservice.dto.OrderRequest;
import com.programmingtechie.orderservice.event.OrderPlacedEvent;
import com.programmingtechie.orderservice.model.Order;
import com.programmingtechie.orderservice.model.OrderLineItems;
import com.programmingtechie.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.sleuth.Span;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.kafka.core.KafkaTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
@Slf4j
public class OrderService {

    private final OrderRepository orderRepository;
    private final InventoryClient inventoryClient;
    private final WebClient.Builder webClientBuilder;
    private final Tracer tracer;
    private final KafkaTemplate<String, OrderPlacedEvent> kafkaTemplate;;
    //private final KafkaTemplate<String, String> kafkaTemplate2;;
    @Value("${application.topic}")
    private String applicationTopic;

    @SneakyThrows
    public String placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream()
                .map(OrderLineItems::getSkuCode)
                .toList();

        // Call Inventory Service, and place order if product is in
        // stock
        log.info("Checking inventory");
        Span inventoryServiceLookup = tracer.nextSpan().name("InventoryServiceLookup");

        //        try(Tracer.SpanInScope inScope = tracer.withSpan(inventoryServiceLookup.start())) {
        try(Tracer.SpanInScope inScope = tracer.withSpan(inventoryServiceLookup.start())) {

            inventoryServiceLookup.tag("call", "inventory-service");

            log.info("skus" + skuCodes);
            // Call Inventory Service, and place order if product is in
            // stock
            InventoryResponse[] inventoryResponsArray = webClientBuilder.build().get()
                    .uri("http://inventory-service/api/inventory",
                            uriBuilder -> uriBuilder.queryParam("skuCode", skuCodes).build())
                    .retrieve()
                    .bodyToMono(InventoryResponse[].class)
                    .block();

            boolean allProductsInStock = inventoryClient.checkStock(skuCodes)
                    .stream()
                    .allMatch(InventoryResponse::isInStock);
            if (allProductsInStock) {
                orderRepository.save(order);
                log.info("order saved");
                log.info("order number: " + order.getOrderNumber());
                var orderKafka = new OrderPlacedEvent(order.getOrderNumber());
                var xcb = orderKafka.toString();
                try {
                    Class<?> c1 = Class.forName("com.programmingtechie.orderservice.event.OrderPlacedEvent");
                    log.info("inicialização com sucesso");
                } catch (Exception e)
                {
                    System.out.println("erro de instancia do objeto" + e.getMessage());
                    throw new Exception(e);
                }
                kafkaTemplate.send(applicationTopic, orderKafka);
//                kafkaTemplate.send("notificationTopic", orderKafka);
                log.info("inicio da chamanda - notification sended");
//                kafkaTemplate2.send("notificationTopic2", xcb);
                log.info("notification sended");
                return "Order Placed Successfully";
            } else {
                throw new IllegalArgumentException("Product is not in stock, please try again later");
            }
        }
        catch (Exception e) {
            log.error("erro : " + e.getMessage() ); //+ "Stack erro: " + Arrays.toString(e.getStackTrace()));
            throw new Exception(e);
        }
        finally {
            inventoryServiceLookup.end();
        }


    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        OrderLineItems orderLineItems = new OrderLineItems();
        orderLineItems.setPrice(orderLineItemsDto.getPrice());
        orderLineItems.setQuantity(orderLineItemsDto.getQuantity());
        orderLineItems.setSkuCode(orderLineItemsDto.getSkuCode());
        return orderLineItems;
    }
}
