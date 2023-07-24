package com.uberClone.uberClone.config;

import com.corundumstudio.socketio.AckRequest;
import jakarta.annotation.PreDestroy;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;

import com.corundumstudio.socketio.Configuration;
import com.corundumstudio.socketio.SocketIOClient;
import com.corundumstudio.socketio.SocketIOServer;
import com.corundumstudio.socketio.listener.ConnectListener;
import com.corundumstudio.socketio.listener.DisconnectListener;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;

@CrossOrigin("*")
@Component

@Log4j2
public class SocketIoConfig {
    @Value("${socket.host}")
    private String host;

    @Value("${socket.port}")
    private Integer port;

    private SocketIOServer server;

    @Bean
    public SocketIOServer socketIOServer() {
        Configuration config = new Configuration();
        config.setHostname(host);
        config.setPort(port);
        config.setOrigin("*");
        System.out.println("Ssl Protocol" + config.getSSLProtocol());
        config.setAllowCustomRequests(true);
//        config.setAddVersionHeader(true);

        server = new SocketIOServer(config);
        server.start();

//        server.addDisconnectListener(new DisconnectListener() {
//            @Override
//            public void onDisconnect(SocketIOClient client) {
//                client.getNamespace().getAllClients().stream().forEach(data-> {
//                    log.info("user disconnected "+data.getSessionId().toString());});
//            }
//        });
        return server;
    }

    @PreDestroy
    public void stopSocketIOServer() {
        this.server.stop();
    }

}
