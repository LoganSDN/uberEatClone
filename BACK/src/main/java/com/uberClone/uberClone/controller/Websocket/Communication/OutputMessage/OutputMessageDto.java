package com.uberClone.uberClone.controller.Websocket.Communication.OutputMessage;


import lombok.*;

@Setter
@ToString
@Getter
//@RequiredArgsConstructor
//@NoArgsConstructor
public class OutputMessageDto {
    public OutputMessageDto(String msg) {
        this.txt = msg;
    }

    public String txt;
//    public String sender;
//    public MessageType type;
}
