package com.example.MoneyTransferService.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class ConfirmationRequest {
    @JsonProperty("operationId")
    private String operationId;

    @JsonProperty("code")
    private String code;
}
