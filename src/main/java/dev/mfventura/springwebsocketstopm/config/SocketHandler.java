package dev.mfventura.springwebsocketstopm.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CopyOnWriteArrayList;

@Component
@Slf4j
public class SocketHandler extends TextWebSocketHandler {
    List<WebSocketSession> sessions = new CopyOnWriteArrayList<>();

    @Override
    public void handleTextMessage(WebSocketSession session, TextMessage message)
            throws IOException {

        log.info("Received message: " + message.getPayload());

        for(WebSocketSession webSocketSession : sessions) {
            if (!webSocketSession.isOpen()){
                webSocketSession.close();
                sessions.remove(webSocketSession);
                continue;
            }
            webSocketSession.sendMessage(new TextMessage("Hello from server!"));
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        //the messages will be broadcasted to all users.
        sessions.add(session);
    }
}
