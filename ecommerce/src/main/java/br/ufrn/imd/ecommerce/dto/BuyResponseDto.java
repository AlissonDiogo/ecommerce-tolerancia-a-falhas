package br.ufrn.imd.ecommerce.dto;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public record BuyResponseDto(HttpStatus status, UUID transactionId) {
}
