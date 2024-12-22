package br.ufrn.imd.ecommerce.model;

import java.util.UUID;

public record Product(UUID id, String name, Double value) {
}
