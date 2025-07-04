package com.OpenChat.chat.chat;


import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.stereotype.Controller;

@Controller
public class ChatController {

    @MessageMapping("/chat.sendMessage")
    @SendTo("/topic/public")
    public ChatMessage sendMessage(
            @Payload ChatMessage chatMessage
    ) {
        return  chatMessage ;
    }

    @MessageMapping("/chat.addUser")
    @SendTo("/topic/public")
    public ChatMessage addUser(
            @Payload ChatMessage chatMessage ,
            SimpMessageHeaderAccessor headerAccessor
    ) {
        // Adding username to WebSocketSession
        headerAccessor.getSessionAttributes().put("username" , chatMessage.getSender()) ;
        headerAccessor.getSessionAttributes().put("senderColor" , chatMessage.getSenderColor()) ;
        return chatMessage ;
    }
}
