package dev.mfventura.springwebsocketstopm.controller;

import dev.mfventura.springwebsocketstopm.model.Message;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

    //HEllo world method
    @MessageMapping("/hello")
    @SendTo("/topic/messages")
    public Message hello(Message message) {
        return Message.builder()
                .name("Server")
                .content("Hello, " + message.getName() + "!")
                .build();
    }

    @MessageMapping("/message")
    @SendTo("/topic/messages")
    public Message handleMessage(Message message) {
        return message;
    }
}
