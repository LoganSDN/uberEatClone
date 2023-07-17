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
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.security.access.prepost.PreAuthorize;
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
//    @RolesAllowed("ROLE_DRIVER")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public void onConnectionDeliveryUpdate(@Header("simpSessionId") String sessionId, SimpMessageHeaderAccessor simp,String token) throws IOException {
        Map<String, Object> attrib = simp.getSessionAttributes();
        System.out.println("Attrib: " + attrib);//TES NUL
        WebSocketSession sessions = (WebSocketSession) attrib.get("WEBSOCKET_SESSION_ATTRIBUTE");
        token = token.replace('\"',' ').trim();
        System.out.println("Token :"+ token);
//        this.simpMessagingTemplate.
        if (!this.jwtTokenUtil.validateAccessTokenWebSocket(token)){
            sessions.close();
          System.out.println("Wrong Token =  in connect" + token);
          this.simpMessagingTemplate.convertAndSendToUser(sessionId, "/connect", false);
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
