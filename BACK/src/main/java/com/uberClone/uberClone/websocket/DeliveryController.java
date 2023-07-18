package com.uberClone.uberClone.websocket;

import com.uberClone.uberClone.dtos.InputMessageDto;
import com.uberClone.uberClone.dtos.OrderDto;
import com.uberClone.uberClone.jwt.JwtTokenUtil;
import com.uberClone.uberClone.services.interfaces.UsersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class DeliveryController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UsersService usersService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

    public DeliveryController(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }

    @MessageMapping("/dely")
    @SendTo("/topic/message")
    public String simpleMessage(InputMessageDto msg) throws Exception{
        System.out.println("Message was " + msg.getTxt());
        return msg.getTxt();
    }

    @MessageMapping("/connect")
    public void onConnectionDeliveryUpdate(@Header("simpSessionId") String sessionId,@Header("Authorization") String tokenHeader, String token) throws IOException {
        token = token.replace('\"',' ').trim();
    System.out.println("Header Token = : "+ tokenHeader);
        System.out.println("Token :"+ token);
        if (!this.jwtTokenUtil.validateAccessTokenWebSocket(token)){
          System.out.println("Wrong Token =  in connect" + token);
        }
    }

    @MessageMapping("/accept")
    public OrderDto acceptOrderDelivery(){
        return new OrderDto();
    }

    @MessageMapping("/reject")
    public OrderDto declineOrderDelivery(){
        return new OrderDto();
    }
}
