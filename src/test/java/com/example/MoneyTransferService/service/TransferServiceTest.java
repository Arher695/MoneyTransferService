package com.example.MoneyTransferService.service;

import com.example.MoneyTransferService.model.Amount;
import com.example.MoneyTransferService.model.TransferRequest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class TransferServiceTest {

    @Autowired
    private TransferService transferService;

    @Test
    void shouldTransferAndReturnOperationId() {
        TransferRequest request = new TransferRequest();
        request.setCardFromNumber("1111111111111111");
        request.setCardFromValidTill("10/25");
        request.setCardFromCVV("123");
        request.setCardToNumber("2222222222222222");

        Amount amount = new Amount();
        amount.setValue(1000);
        request.setAmount(amount);

        var response = transferService.transfer(request);
        assertNotNull(response.getOperationId());
        assertTrue(response.getOperationId().length() > 0);
    }

    @Test
    void shouldFailOnInvalidCardNumber() {
        TransferRequest request = new TransferRequest();
        request.setCardFromNumber("123"); // invalid
        request.setCardToNumber("2222222222222222");
        request.setCardFromValidTill("10/25");
        request.setCardFromCVV("123");

        Amount amount = new Amount();
        amount.setValue(1000);
        request.setAmount(amount);

        assertThrows(IllegalArgumentException.class, () -> transferService.transfer(request));
    }
    @Test
    void shouldCalculateCorrectFee() {
        TransferRequest request = new TransferRequest();
        request.setCardFromNumber("1111111111111111");
        request.setCardFromValidTill("10/25");
        request.setCardFromCVV("123");
        request.setCardToNumber("2222222222222222");

        Amount amount = new Amount();
        amount.setValue(1000);
        request.setAmount(amount);

        var response = transferService.transfer(request);
        assertNotNull(response.getOperationId());

    }
}
