package com.uberClone.uberClone.config.Websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.ChannelRegistration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.config.annotation.*;
import org.springframework.web.socket.handler.WebSocketHandlerDecoratorFactory;
import org.springframework.web.socket.server.HandshakeInterceptor;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketBrokerConfig implements WebSocketMessageBrokerConfigurer {

    @Autowired
    private WebSocketInterceptor webSocketInterceptor;
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic","/user");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/ws")
                .setAllowedOrigins("http://localhost:4200")
                .withSockJS()
//                .setInterceptors(handshakeInterceptor())
        ;
    }
    @Override
    public void configureClientInboundChannel(ChannelRegistration registration)
    { registration.interceptors(webSocketInterceptor); }

//    @Bean
//    public HandshakeInterceptor handshakeInterceptor() {
//        return new CustomHandshakeInterceptor();
//    }
    @Override
    public void configureWebSocketTransport(WebSocketTransportRegistration registration) {
        registration.addDecoratorFactory(handlerDecoratorFactory());
    }

    @Bean
    public WebSocketHandlerDecoratorFactory handlerDecoratorFactory() {
        return new CustomWebSocketHandlerDecoratorFactory();
    }

    private static class CustomWebSocketHandlerDecoratorFactory implements WebSocketHandlerDecoratorFactory {
        @Override
        public WebSocketHandler decorate(WebSocketHandler handler) {
            return new CustomWebSocketHandler(handler);
        }
    }
};