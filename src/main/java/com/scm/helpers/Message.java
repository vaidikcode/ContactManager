package com.scm.helpers;

import lombok.*;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Message {
    private String content;
    private MessageType type = MessageType.green;
}
