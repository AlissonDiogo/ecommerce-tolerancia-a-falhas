package br.ufrn.imd.ecommerce.dto;

import org.springframework.http.HttpStatus;

public record BuyResponseDto(HttpStatus status, Integer buyId) {
}
