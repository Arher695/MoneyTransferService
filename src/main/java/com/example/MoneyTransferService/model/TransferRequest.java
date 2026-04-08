package com.example.MoneyTransferService.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class TransferRequest {
    @JsonProperty("cardFromNumber")
    private String cardFromNumber;

    @JsonProperty("cardFromValidTill")
    private String cardFromValidTill;

    @JsonProperty("cardFromCVV")
    private String cardFromCVV;

    @JsonProperty("cardToNumber")
    private String cardToNumber;

    @JsonProperty("amount")
    private Amount amount;

}
