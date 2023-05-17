package com.backend.pojo.client;

import lombok.Data;

@Data
public class Token {
    private String token;
    private Integer type;

    public Token(String token, Integer type) {
        this.token = token;
        this.type = type;
    }

    public Token(String token) {
        this.token = token;
    }
}
