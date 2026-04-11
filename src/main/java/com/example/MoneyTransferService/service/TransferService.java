package com.example.MoneyTransferService.service;

import com.example.MoneyTransferService.model.ConfirmationRequest;
import com.example.MoneyTransferService.model.TransferRequest;
import com.example.MoneyTransferService.model.TransferResponse;
import org.springframework.stereotype.Service;

import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

@Service
public class TransferService {

    private final Map<String, TransferRequest> transfers = new HashMap<>();
    private final Map<String, String> confirmationCodes = new HashMap<>();

    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TransferResponse transfer(TransferRequest request) {
        validateTransferRequest(request);

        // Генерируем ID операции
        String operationId = UUID.randomUUID().toString();
        transfers.put(operationId, request);

        // Генерируем код подтверждения
        String code = String.format("%04d", new Random().nextInt(10000));
        confirmationCodes.put(operationId, code);

        // Логируем операцию
        logOperation(
                "TRANSFER_INIT",
                request.getCardFromNumber(),
                request.getCardToNumber(),
                request.getAmount().getValue(),
                calculateFee(request.getAmount().getValue()),
                "PENDING"
        );

        TransferResponse response = new TransferResponse();
        response.setOperationId(operationId);
        return response;
    }

    public TransferResponse confirm(ConfirmationRequest request) {
        if (!transfers.containsKey(request.getOperationId())) {
            throw new IllegalArgumentException("Operation not found");
        }
        if (request.getCode() == null || request.getCode().length() != 4) {
            throw new IllegalArgumentException("Invalid confirmation code");
        };
//        String expectedCode = confirmationCodes.get(request.getOperationId());
//        if (!expectedCode.equals(request.getCode())) {
//            logOperation(
//                    "CONFIRM",
//                    transfers.get(request.getOperationId()).getCardFromNumber(),
//                    transfers.get(request.getOperationId()).getCardToNumber(),
//                    transfers.get(request.getOperationId()).getAmount().getValue(),
//                    calculateFee(transfers.get(request.getOperationId()).getAmount().getValue()),
//                    "FAILED (wrong code)"
//            );
//            throw new IllegalArgumentException("Invalid confirmation code");
//        }

        TransferRequest transferReq = transfers.get(request.getOperationId());

        // Здесь можно списать средства (в реальности — с карты)
        // В нашем случае — просто эмулируем успех

        logOperation(
                "CONFIRM",
                transferReq.getCardFromNumber(),
                transferReq.getCardToNumber(),
                transferReq.getAmount().getValue(),
                calculateFee(transferReq.getAmount().getValue()),
                "SUCCESS"
        );

        TransferResponse response = new TransferResponse();
        response.setOperationId(request.getOperationId());
        return response;
    }

    private void validateTransferRequest(TransferRequest request) {
        if (request.getCardFromNumber() == null || request.getCardFromNumber().length() != 16) {
            throw new IllegalArgumentException("Invalid card from number");
        }
        if (request.getCardToNumber() == null || request.getCardToNumber().length() != 16) {
            throw new IllegalArgumentException("Invalid card to number");
        }
        if (request.getCardFromCVV() == null || request.getCardFromCVV().length() != 3) {
            throw new IllegalArgumentException("Invalid CVV");
        }
        if (request.getAmount() == null || request.getAmount().getValue() == null || request.getAmount().getValue() <= 0) {
            throw new IllegalArgumentException("Invalid amount");
        }
    }

    private int calculateFee(Long amount) {
        return (int) (amount * 0.01); // 1%
    }

    private void logOperation(String type, String from, String to, Long amount, int fee, String result) {
        try (FileWriter fw = new FileWriter("logs/transfers.log", true)) {
            String line = String.format("[%s] %s | FROM: %s | TO: %s | AMOUNT: %d RUB | FEE: %d RUB | RESULT: %s%n",
                    LocalDateTime.now().format(formatter),
                    type,
                    from,
                    to,
                    amount,
                    fee,
                    result
            );
            fw.write(line);
        } catch (IOException e) {
            System.err.println("Failed to write to log: " + e.getMessage());
        }
    }
}
