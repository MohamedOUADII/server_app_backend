package com.example.fullstackapp.enumeration;

import lombok.Getter;

@Getter
public enum Status {
    Server_Down("Server_Down"),
    Server_Up("Server_Up");

    private final String status;

    Status(String status) {
        this.status = status;
    }
}
