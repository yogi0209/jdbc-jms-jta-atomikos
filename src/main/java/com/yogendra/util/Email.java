package com.yogendra.util;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Email {
    private String to;
    private String body;
}
