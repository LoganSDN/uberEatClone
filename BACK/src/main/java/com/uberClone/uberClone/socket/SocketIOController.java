package com.uberClone.uberClone.socket;

import com.corundumstudio.socketio.AckRequest;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.annotation.OnEvent;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DataListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import com.uberClone.uberClone.entities.User;
import com.uberClone.uberClone.jwt.JwtTokenUtil;
import com.uberClone.uberClone.services.interfaces.OrderService;
import com.uberClone.uberClone.services.interfaces.UsersService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.util.Optional;

@Component
@Log4j2
public class SocketIOController {
    @Autowired
    private SocketIOServer socketServer;

    @Autowired
    private UsersService usersService;

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Autowired
    private OrderService orderService;


    SocketIOController(SocketIOServer socketServer){
//        socketServer.getConfiguration().setOrigin("http://localhost:4200");
        this.socketServer = socketServer;
        this.socketServer.addConnectListener(onUserConnectWithSocket);
        this.socketServer.addDisconnectListener(onUserDisconnectWithSocket);
        this.socketServer.addEventListener("messageSendToUser", Message.class, onSendMessage);
//        this.socketServer.addEventListener("DriverResponse", DriverResponse.class, onDriverResponse);
    }
    @OnEvent("DriverResponse")
    public void onDriverResponse(SocketIOClient client, AckRequest ackSender, DriverResponse data) throws Exception {
        log.info("Yes Received response: " +  data.getOrderId() + " " + data.isStatus());
        User user = usersService.getUserById(data.getDriverId()).orElse(null);

        if (data.isStatus()){
            orderService.changeDrivenStatus(data.getOrderId());
            user.setStatus("DELIVERING");
            usersService.updateUser(user);
            usersService.removeRejectedList(data.getOrderId());
        }
        else{
            System.out.println("I got denied : " + client.getSessionId());
            usersService.addRejectedList(data.getOrderId(), client.getSessionId());
//                    wait(2000);
            usersService.findDriverForOrder(orderService.getOrderById( data.getOrderId()));
        }
    }



    public ConnectListener onUserConnectWithSocket = new ConnectListener() {
        @Override
        public void onConnect(SocketIOClient client) {
            log.info("Perform operation on user connect in controller : " + client.getSessionId());
            String token = client.getHandshakeData().getHttpHeaders().get("authorization");
            if (!jwtTokenUtil.validateAccessTokenWebSocket(token)){
                log.warn("Jwt was wrong or not set :" + token);
                client.disconnect();
                return;
            }
            Long id = jwtTokenUtil.getId(token);
//            System.out.println("Client.handshake" + client.getHandshakeData().getHttpHeaders() + " id " + id );
            User user = usersService.getUserById(id).orElse(null);

            if (user == null){
                System.out.print("Could not find user");
                client.disconnect();
                return;
            }
            usersService.updateSocketUser(user, client.getSessionId());
//            System.out.println("USer : " + user.toString() + " but still left ");
        }
    };


    public DisconnectListener onUserDisconnectWithSocket = new DisconnectListener() {
        @Override
        public void onDisconnect(SocketIOClient client) {
            log.info("Perform operation on user disconnect in controller");
            Optional<User> user = usersService.getBySocketId(client.getSessionId());
            if (user.isPresent()){
                usersService.updateSocketUser(user.get(), null);
            }
        }
    };

    public DataListener<Message> onSendMessage = new DataListener<Message>() {
        @Override
        public void onData(SocketIOClient client, Message message, AckRequest acknowledge) throws Exception {

            log.info(message.getSenderName()+" user send message to user "+message.getTargetUserName()+" and message is "+message.getMessage());
            socketServer.getBroadcastOperations().sendEvent(message.getTargetUserName(),client, message);

            acknowledge.sendAckData("Message send to target user successfully");
        }

    };
//        public DataListener<DriverResponse> onDriverResponse = new DataListener<DriverResponse>() {
//            @Override
//            public void onData(SocketIOClient client, DriverResponse data, AckRequest ackSender) throws Exception {
//                log.info("Yes Received response: " +  data.getOrderId() + " " + data.isStatus());
//                User user = usersService.getUserById(data.getDriverId()).orElse(null);
//                if (data.isStatus()){
//                    orderService.changeDrivenStatus(data.getOrderId());
//                    user.setStatus("DELIVERING");
//                    usersService.updateUser(user);
//                    usersService.removeRejectedList(data.getOrderId());
//                }
//                else{
//                    System.out.println("I got denied : " + client.getSessionId());
//                    usersService.addRejectedList(data.getOrderId(), client.getSessionId());
////                    wait(2000);
//                    usersService.findDriverForOrder(orderService.getOrderById( data.getOrderId()));
//                }
//            }
//        };
}
