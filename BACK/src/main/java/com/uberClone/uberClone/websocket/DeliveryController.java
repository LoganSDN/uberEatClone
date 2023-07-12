package com.uberClone.uberClone.websocket;

import com.uberClone.uberClone.dtos.InputMessageDto;
import com.uberClone.uberClone.dtos.OutputMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DeliveryController {

    @MessageMapping("/dely")
    @SendTo("/topic/message")
    public String simpleMessage(InputMessageDto msg) throws Exception{
        System.out.println("Message was " + msg.getTxt());
        return msg.getTxt();
    }
}
