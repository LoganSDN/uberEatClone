package com.uberClone.uberClone.config.Websocket;
import com.uberClone.uberClone.jwt.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class WebSocketInterceptor  implements ChannelInterceptor {

    @Autowired
    private JwtTokenUtil jwtTokenUtil;

    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(message);

        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            String jwtToken = accessor.getFirstNativeHeader("Authorization");
            if (jwtToken != null && jwtTokenUtil.validateAccessTokenWebSocket(jwtToken)) {
                // Extract user details from the JWT token
                String username = jwtTokenUtil.getUsername(jwtToken);
                List<String> roles = jwtTokenUtil.getRoles(jwtToken);

                // Create a list of GrantedAuthority based on the user roles
                List<GrantedAuthority> authorities = roles.stream()
                        .map(role -> new SimpleGrantedAuthority(role))
                        .collect(Collectors.toList());

                // Create an Authentication object
                Authentication authentication = new UsernamePasswordAuthenticationToken(username, null, authorities);

                // Set the authentication object to the SecurityContext
                SecurityContextHolder.getContext().setAuthentication(authentication);

                // Add the session ID to the session attributes
                accessor.getSessionAttributes().put("WEBSOCKET_SESSION_ATTRIBUTE", accessor.getSessionAttributes());
            }
        }

        return message;
    }

}