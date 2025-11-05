package io.github.platovd.demo_messenger.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.github.platovd.demo_messenger.dto.request.WebsocketRequest;
import io.github.platovd.demo_messenger.dto.response.WebsocketResponse;
import io.github.platovd.demo_messenger.websocket.ResponseStatus;
import io.github.platovd.demo_messenger.websocket.WebsocketSessionManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class ChatService {
    private final ObjectMapper mapper;
    private final WebsocketSessionManager sessionManager;

    public void processMessage(WebSocketSession session, String json) throws IOException {
        WebsocketRequest request;
        try {
            request = mapper.readValue(json, WebsocketRequest.class);
        } catch (JsonProcessingException e) {
            handleWrongRequestFormat(session, json, e);
            return;
        }
        routeMessage(request, session);
    }

    private void routeMessage(WebsocketRequest request, WebSocketSession session) throws IOException {
        switch (request.getMessageType()) {
            case MESSAGE -> handleMessage(request.getTopic(), request.getContent());
            case SUBSCRIBE -> handleSubscribe(request.getTopic(), session);
            case UNSUBSCRIBE -> handleUnsubscribe(request.getTopic(), session);
        }
    }

    private void handleMessage(String topic, String message) throws IOException {
        Set<WebSocketSession> receivers = sessionManager.getTopicSubscribers(topic);
        String response = mapper.writeValueAsString(buildResponseMessage(message, ResponseStatus.OK));
        for (WebSocketSession session : receivers) {
            session.sendMessage(new TextMessage(response));
        }
    }

    private void handleSubscribe(String topic, WebSocketSession session) {
        sessionManager.subscribeToTopic(session, topic);
    }

    private void handleUnsubscribe(String topic, WebSocketSession session) {
        sessionManager.unsubscribeFromTopic(session, topic);
    }

    public void newSession(WebSocketSession session) {
        sessionManager.createNewSession(session);
    }

    public void removeSession(WebSocketSession session) {
        sessionManager.removeSession(session);
    }

    private WebsocketResponse buildResponseMessage(String message, ResponseStatus status) {
        return WebsocketResponse.builder()
                .responseStatus(status)
                .dateTime(LocalDateTime.now())
                .message(message)
                .build();
    }

    private void handleWrongRequestFormat(WebSocketSession session, String json, JsonProcessingException e) throws IOException {
        String messageToResponse = "Error during processing message. " + json + '\n' + e.getMessage();
        String response = mapper.writeValueAsString(buildResponseMessage(messageToResponse, ResponseStatus.ERROR));
        session.sendMessage(new TextMessage(response));
    }
}
