package com.example.SafeLoot.controller.passStorage;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class PasswordRequest {
    private String passName;
    private String password;

    private String url;

    private String email;
}
