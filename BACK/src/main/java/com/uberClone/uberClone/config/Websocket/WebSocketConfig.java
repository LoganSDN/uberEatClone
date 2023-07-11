//package com.uberClone.uberClone.config.Websocket;
//
//import com.uberClone.uberClone.controller.Websocket.Communication.OutputMessage.OutputMessageDto;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.security.config.annotation.web.socket.EnableWebSocketSecurity;
//import org.springframework.web.socket.TextMessage;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.WebSocketSession;
//import org.springframework.web.socket.config.annotation.*;
//import org.springframework.web.socket.handler.TextWebSocketHandler;
//
//@Configuration
//@EnableWebSocket
////@EnableWebSocketMessageBroker
////@EnableWebSocketSecurity
//public class WebSocketConfig implements WebSocketConfigurer {
//    @Override
//    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
//        registry.addHandler(webSocketHandler(), "/ws");
//    }
//    @Bean
//    public WebSocketHandler webSocketHandler() {
//        return new ServerWebSocketHandler();
//    }
//    @Override
//    public void configureMessageBroker(MessageBrokerRegistry config) {
//        config.enableSimpleBroker("/topic");
//        config.setApplicationDestinationPrefixes("/app");
//    }
//
//    @Override
//    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        registry.addEndpoint("/ws").withSockJS();
//
//    }

//    @Bean
//    AuthorizationManager<Message<?>> authorizationManager(MessageMatcherDelegatingAuthorizationManager.Builder messages) {
//        messages.simpDestMatchers("/ws").permitAll()
//                .simpDestMatchers("/ws/**").permitAll()
//                .anyMessage().permitAll();
//        return messages.build();
//    }

//}
