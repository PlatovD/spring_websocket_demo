package io.github.platovd.demo_messenger.dto.response;


import io.github.platovd.demo_messenger.websocket.ResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@Builder
public class WebsocketResponse {
    private ResponseStatus responseStatus;
    private String message;
    private LocalDateTime dateTime;
}
