package io.github.platovd.demo_messenger.websocket;


import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.*;

@Component
public class WebsocketSessionManager {
    private final Map<WebSocketSession, Set<String>> subscribedTopics = Collections.synchronizedMap(new HashMap<>());
    private final Map<String, Set<WebSocketSession>> topicsSubscribers = Collections.synchronizedMap(new HashMap<>());

    public void createNewSession(WebSocketSession session) {
        if (subscribedTopics.containsKey(session)) return;
        subscribedTopics.put(session, Collections.synchronizedSet(new HashSet<>()));
    }

    public void removeSession(WebSocketSession session) {
        if (!subscribedTopics.containsKey(session)) return;
        synchronized (subscribedTopics) {
            for (String topic : subscribedTopics.get(session)) {
                if (!topicsSubscribers.containsKey(topic)) continue;
                topicsSubscribers.get(topic).remove(session);
            }
            subscribedTopics.remove(session);
        }
    }

    public void subscribeToTopic(WebSocketSession session, String topic) {
        if (!subscribedTopics.containsKey(session)) return;

        topic = processTopic(topic);
        if (subscribedTopics.get(session).contains(topic)) return;
        if (!topicsSubscribers.containsKey(topic)) createNewTopic(topic);
        subscribedTopics.get(session).add(topic);
        topicsSubscribers.get(topic).add(session);
    }

    public void unsubscribeFromTopic(WebSocketSession session, String topic) {
        if (!subscribedTopics.containsKey(session)) return;

        topic = processTopic(topic);
        if (!subscribedTopics.get(session).contains(topic)) return;
        subscribedTopics.get(session).remove(topic);
        if (!topicsSubscribers.get(topic).contains(session)) return;
        topicsSubscribers.get(topic).remove(session);
    }

    public Set<WebSocketSession> getTopicSubscribers(String topic) {
        topic = processTopic(topic);
        if (!topicsSubscribers.containsKey(topic)) return new HashSet<>();
        return new HashSet<>(topicsSubscribers.get(topic));
    }

    public Set<String> getSubscribedTopics(WebSocketSession session) {
        if (!subscribedTopics.containsKey(session)) return new HashSet<>();
        return new HashSet<>(subscribedTopics.get(session));
    }

    private String processTopic(String topic) {
        return topic.strip().toLowerCase();
    }

    private void createNewTopic(String topic) {
        topicsSubscribers.put(topic, Collections.synchronizedSet(new HashSet<>()));
    }
}
