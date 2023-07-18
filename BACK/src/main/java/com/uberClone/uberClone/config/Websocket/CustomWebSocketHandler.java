package com.uberClone.uberClone.config.Websocket;

import com.uberClone.uberClone.websocket.WebsocketSessionHolder;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;


public class CustomWebSocketHandler  implements WebSocketHandler {
    private WebsocketSessionHolder websocketSessionHolder;
    private final WebSocketHandler delegate;

    public CustomWebSocketHandler(WebSocketHandler handler) {
        this.delegate = handler;
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        this.websocketSessionHolder.addSession("sessionId", session);
        System.out.println("ADDED A SESSION: "+ websocketSessionHolder.getSession("sessionId"));
        delegate.afterConnectionEstablished(session);
    }

    @Override
    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        delegate.handleMessage(session, message);

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        delegate.handleTransportError(session, exception);

    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus closeStatus) throws Exception {
        delegate.afterConnectionClosed(session, closeStatus);

    }

    @Override
    public boolean supportsPartialMessages() {
        return delegate.supportsPartialMessages();
    }
}
