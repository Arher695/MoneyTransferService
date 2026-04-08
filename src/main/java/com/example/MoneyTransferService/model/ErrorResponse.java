package com.example.MoneyTransferService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ErrorResponse {
    @JsonProperty("message")
    private String message;

    @JsonProperty("id")
    private Integer id;

    public ErrorResponse(String message, Integer id) {
        this.message = message;
        this.id = id;
    }
}
