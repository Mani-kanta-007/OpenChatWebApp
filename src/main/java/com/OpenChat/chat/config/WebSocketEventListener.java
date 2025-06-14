package com.OpenChat.chat.config;


import com.OpenChat.chat.chat.ChatMessage;
import com.OpenChat.chat.chat.MessageType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
@Slf4j
public class WebSocketEventListener {

    private final SimpMessageSendingOperations messageTemplate ;

    public WebSocketEventListener(SimpMessageSendingOperations messageTemplate) {
        this.messageTemplate = messageTemplate;
    }

    @EventListener
    public void handleWebSocketDisconnectListener(
            SessionDisconnectEvent event
    ) {
        StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(event.getMessage()) ;
        String username = (String) headerAccessor.getSessionAttributes().get("username") ;

        if(username != null) {
            //log.info("User Disconnected : {}" , username);
            var chatMessage = ChatMessage.builder()
                    .messageType(MessageType.LEFT)
                    .sender(username)
                    .build() ;
            messageTemplate.convertAndSend("/topic/public" , chatMessage);
        }
    }
}
