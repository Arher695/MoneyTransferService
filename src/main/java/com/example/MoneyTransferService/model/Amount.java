package com.example.MoneyTransferService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Amount {
    @JsonProperty("value")
    private Long value;

    @JsonProperty("currency")
    private String currency = "RUB";


}
