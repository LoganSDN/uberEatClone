package com.uberClone.uberClone.controller;

import com.uberClone.uberClone.dtos.MessageDto;
import com.uberClone.uberClone.entities.Order;
import com.uberClone.uberClone.services.interfaces.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.EmitterProcessor;
import reactor.core.publisher.Flux;
import reactor.core.publisher.SignalType;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/order-status")
public class OrderStatusController {
    @Autowired
    private OrderService orderService;

    private Map<String, EmitterProcessor<MessageDto>> emitterProcessors = new HashMap<>();

    @GetMapping(value = "/next-status/{id}")
    public void sendOrderStatusEvent(@PathVariable("id") String id, @RequestBody() MessageDto message) {
        Order order = orderService.getOrderById(Long.parseLong(id));
        order.setStatus(message.getTxt());
        orderService.updateOrder(id, order);

        EmitterProcessor<MessageDto> emitterProcessor = emitterProcessors.get(id);
        if (emitterProcessor != null) {
            emitterProcessor.onNext(message);
        }
    }

    @GetMapping(value = "/order/{id}", consumes = MediaType.ALL_VALUE, produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<ServerSentEvent<MessageDto>> streamEvents(@PathVariable("id") String id) {
        emitterProcessors.putIfAbsent(id, EmitterProcessor.create());
        EmitterProcessor<MessageDto> emitterProcessor = emitterProcessors.get(id);

        return emitterProcessor.map(data -> ServerSentEvent.builder(data).build())
                .doFinally(signalType -> {
                    if (signalType.equals(SignalType.CANCEL)) {
                        emitterProcessors.remove(id);
                    }
                });
    }
}
