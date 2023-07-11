package com.uberClone.uberClone.controller.Websocket;

import com.uberClone.uberClone.controller.Websocket.Communication.OutputMessage.OutputMessageDto;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class DeliveryController {

    @MessageMapping("/dely")
    @SendTo("/topic/message")
    public OutputMessageDto simpleMessage(OutputMessageDto msg) throws Exception{
        System.out.println("Message was " + msg.txt);
        return msg;
    }
}
