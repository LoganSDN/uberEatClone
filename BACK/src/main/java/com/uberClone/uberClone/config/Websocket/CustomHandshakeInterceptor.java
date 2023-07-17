package com.uberClone.uberClone.config.Websocket;

import com.uberClone.uberClone.websocket.WebsocketSessionHolder;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;

import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;

public class CustomHandshakeInterceptor implements HandshakeInterceptor{

    @Override
    public boolean beforeHandshake(ServerHttpRequest request, ServerHttpResponse response,
                                   WebSocketHandler wsHandler, Map<String, Object> attributes) throws Exception {
       System.out.println("CUSTOM INTERCEPTOR");
        attributes.put("attributeName", "attributeValue");

        return true; // Allow the handshake
    }

    @Override
    public void afterHandshake(ServerHttpRequest request, ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {
        // After handshake, perform any additional actions if needed
    }

    private boolean isJwtValid(ServerHttpRequest request) {
        // Perform JWT validation
        return true; // Replace with your logic
    }

    private WebsocketSessionHolder websocketSessionHolder;



}