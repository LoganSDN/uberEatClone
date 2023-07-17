package com.uberClone.uberClone.websocket;

import com.uberClone.uberClone.dtos.InputMessageDto;
import com.uberClone.uberClone.dtos.OrderDto;
import com.uberClone.uberClone.entities.User;
import com.uberClone.uberClone.jwt.JwtTokenUtil;
import com.uberClone.uberClone.services.interfaces.UsersService;
import jakarta.annotation.security.RolesAllowed;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.net.http.WebSocket;
import java.util.Map;
import java.util.Optional;

@Controller
public class DeliveryController {
    private final SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private UsersService usersService;
    @Autowired
    JwtTokenUtil jwtTokenUtil;

//    @Autowired
//    WebSocketRegistryListener webSocketRegistryListener;
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
    @RolesAllowed("ROLE_DRIVER")
    public void onConnectionDeliveryUpdate(@Header("simpSessionId") String sessionId,@Header("authorization") String tokenHeader, String token) throws IOException {
//        this.usersServce.updateUser()i
        token = token.replace('\"',' ').trim();
    System.out.println("Header Token = : "+ tokenHeader);
//        WebSocketSession session = simpMessagingTemplate.getW;
        System.out.println("Token :"+ token);
        if (!this.jwtTokenUtil.validateAccessTokenWebSocket(token)){
            ;
          System.out.println("Wrong Token =  in connect" + token);
        }
//        System.out.println("Right Token =  in connect" + this.jwtTokenUtil.getRoles(token));
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
