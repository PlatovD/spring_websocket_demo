package io.github.platovd.demo_messenger.dto.request;

import io.github.platovd.demo_messenger.websocket.MessageType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebsocketRequest {
    private MessageType messageType;
    private String topic;
    private String content;
}
