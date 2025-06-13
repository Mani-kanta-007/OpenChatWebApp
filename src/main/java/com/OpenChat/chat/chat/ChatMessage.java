package com.OpenChat.chat.chat;

import lombok.*;

//@Getter
//@Setter
//@AllArgsConstructor
//@NoArgsConstructor
//@Builder
//public class ChatMessage {
//    private String content ;
//    private String sender ;
//    private MessageType messageType ;
//
//
//}

public class ChatMessage {

    private String content;
    private String sender;
    private MessageType messageType;
    private  String senderColor ;

    // 1. No-argument constructor (replaces @NoArgsConstructor)
    public ChatMessage() {
    }

    // 2. All-argument constructor (replaces @AllArgsConstructor)
    public ChatMessage(String content, String sender, MessageType messageType, String senderColor) {
        this.content = content;
        this.sender = sender;
        this.messageType = messageType;
        this.senderColor = senderColor ;
    }

    // 3. Getters (replaces @Getter)
    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }
    public String getSenderColor() {
        return senderColor ;
    }
    public MessageType getMessageType() {
        return messageType;
    }

    // 4. Setters (replaces @Setter)
    public void setContent(String content) {
        this.content = content;
    }

    public void setSenderColor(String senderColor) {
        this.senderColor = senderColor;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public void setMessageType(MessageType messageType) {
        this.messageType = messageType;
    }

    // Optional: toString() method for better debugging
    @Override
    public String toString() {
        return "ChatMessage{" +
                "content='" + content + '\'' +
                ", sender='" + sender + '\'' +
                ", messageType=" + messageType +'\'' +
                ", senderColor=" + senderColor +
                '}';
    }

    // 5. Builder pattern implementation (replaces @Builder)
    // This is a static inner class that provides a fluent API to build ChatMessage objects.
    public static ChatMessageBuilder builder() {
        return new ChatMessageBuilder();
    }

    public static class ChatMessageBuilder {
        private String content;
        private String sender;
        private MessageType messageType;
        private  String senderColor ;

        // Private constructor for the builder
        private ChatMessageBuilder() {}

        public ChatMessageBuilder content(String content) {
            this.content = content;
            return this; // Return the builder itself for method chaining
        }

        public ChatMessageBuilder sender(String sender) {
            this.sender = sender;
            return this; // Return the builder itself for method chaining
        }

        public ChatMessageBuilder messageType(MessageType messageType) {
            this.messageType = messageType;
            return this; // Return the builder itself for method chaining
        }

        public ChatMessageBuilder senderColor(String senderColor) {
            this.senderColor = senderColor;
            return this; // Return the builder itself for method chaining
        }


        public ChatMessage build() {
            // Construct the ChatMessage using the all-argument constructor
            // or by setting fields directly after creating an empty ChatMessage.
            return new ChatMessage(content, sender, messageType, senderColor);
        }
    }
}
