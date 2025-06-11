package br.edu.ibmec.cloud.Ecommerce.controllers;

import br.edu.ibmec.cloud.Ecommerce.dtos.card.*;
import br.edu.ibmec.cloud.Ecommerce.useCases.creditCards.CheckCreditCardBalanceUseCase;
import br.edu.ibmec.cloud.Ecommerce.useCases.creditCards.CreateCreditCardUseCase;
import br.edu.ibmec.cloud.Ecommerce.useCases.creditCards.DeleteCreditCardUseCase;
import br.edu.ibmec.cloud.Ecommerce.useCases.creditCards.ValidateCreditCardUseCase;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/cards")
@RequiredArgsConstructor
public class CreditCardController {

    private final CreateCreditCardUseCase createUseCase;
    private final ValidateCreditCardUseCase validateUseCase;
    private final CheckCreditCardBalanceUseCase checkCreditCardBalanceUseCase;
    private final DeleteCreditCardUseCase deleteCreditCardUseCase;

    @PostMapping("/users/{userId}")
    public ResponseEntity<?> createCard(
            @PathVariable String userId,
            @Valid @RequestBody CreateCreditCardDto dto
    ) {
        try {
            createUseCase.execute(userId, dto);
            return ResponseEntity.ok().build();
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), 400);
        } catch (Exception e) {
            return errorResponse("Unexpected error: " + e.getMessage(), 500);
        }
    }

    @PostMapping("/validate")
    public ResponseEntity<?> validateCard(
            @Valid @RequestBody ValidateCreditCardRequestDto dto
    ) {
        try {
            ValidateCreditCardDto result = validateUseCase.execute(dto);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), 400);
        } catch (Exception e) {
            return errorResponse("Unexpected error: " + e.getMessage(), 500);
        }
    }

    @PostMapping("/check-balance")
    public ResponseEntity<?> checkBalance(
            @Valid @RequestBody CheckCreditCardBalanceRequestDto dto
    ) {
        try {
            CheckCreditCardBalanceResponseDto result = checkCreditCardBalanceUseCase.execute(dto);
            return ResponseEntity.ok(result);
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), 400);
        } catch (Exception e) {
            return errorResponse("Unexpected error: " + e.getMessage(), 500);
        }
    }

    @DeleteMapping("/{cardId}")
    public ResponseEntity<?> deleteCard(@PathVariable String cardId) {
        try {
            deleteCreditCardUseCase.execute(cardId);
            return ResponseEntity.noContent().build();
        } catch (IllegalArgumentException e) {
            return errorResponse(e.getMessage(), 400);
        } catch (Exception e) {
            return errorResponse("Unexpected error: " + e.getMessage(), 500);
        }
    }

    private ResponseEntity<Map<String, String>> errorResponse(String message, int status) {
        Map<String, String> error = new HashMap<>();
        error.put("error", message);
        return ResponseEntity.status(status).body(error);
    }
}
