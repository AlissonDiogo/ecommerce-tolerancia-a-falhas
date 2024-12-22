package br.ufrn.imd.ecommerce.dto;

import java.util.UUID;

public record BuyRequestDto(UUID productId, String userId, Boolean ft) {
}
