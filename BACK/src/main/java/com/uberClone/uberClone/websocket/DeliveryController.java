package com.uberClone.uberClone.websocket;

import com.uberClone.uberClone.dtos.InputMessageDto;
import com.uberClone.uberClone.dtos.OrderDto;
import com.uberClone.uberClone.dtos.OutputMessageDto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;

import java.net.Socket;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Controller
public class DeliveryController {
    private final SimpMessagingTemplate simpMessagingTemplate;
    private Set<String> userlist = new HashSet<>();

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
    public void onConnectionDeliveryUpdate(@Header("simpSessionId") String sessionId){
        userlist.add(sessionId);
        userlist.forEach(name -> {
            if (!name.equals(sessionId)) {
            System.out.println("Session ID: " + sessionId + " sending to " + name);
                 this.simpMessagingTemplate.convertAndSendToUser(name,"/bite",sessionId +" Connected");
//                this.simpMessagingTemplate.convertAndSend("/user/"+name, "");
            }});
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
