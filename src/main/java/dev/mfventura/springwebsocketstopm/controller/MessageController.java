package dev.mfventura.springwebsocketstopm.controller;

import dev.mfventura.springwebsocketstopm.model.Message;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class MessageController {

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
        System.out.println(message.getName()+" hizo SEND");
        return message;
    }

    @SubscribeMapping("/messages")
    public Message subscribe(@Header("user") String user) {
        System.out.println(user+" se suscribio");
        return Message.builder()
                .name("Server")
                .content(user + " connected")
                .build();
    }
}
