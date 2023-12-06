package dev.mfventura.springwebsocketstopm.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Message {
    private String name;
    private String content;
}
