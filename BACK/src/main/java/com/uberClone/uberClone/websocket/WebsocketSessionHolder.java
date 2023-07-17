package com.uberClone.uberClone.websocket;

import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class WebsocketSessionHolder {

    static {
        sessions = new HashMap<>();
    }

    // key - username, value - List of user's sessions
    private static Map<String, WebSocketSession> sessions;

    public static void addSession(String username, WebSocketSession session)
    {
        synchronized (sessions) {
            sessions.put(username, session);
        }
    }

    public static void closeSessions(String username) throws IOException
    {
        synchronized (sessions) {
            var userSessions = sessions.get(username);
            if (userSessions != null)
            {
                // I use POLICY_VIOLATION to indicate reason of disconnecting for a client
                userSessions.close(CloseStatus.POLICY_VIOLATION);
                sessions.remove(username);
            }
        }
    }
}