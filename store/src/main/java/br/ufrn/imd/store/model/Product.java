package br.ufrn.imd.store.model;

import java.util.UUID;

public record Product(UUID id, String name, Double value) {
}
