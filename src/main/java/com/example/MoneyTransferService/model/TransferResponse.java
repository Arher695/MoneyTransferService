package com.example.MoneyTransferService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransferResponse {
    @JsonProperty("operationId")
    private String operationId;


}
