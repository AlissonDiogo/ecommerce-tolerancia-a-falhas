package br.ufrn.imd.ecommerce.utils.fails;

public enum FailType {
    OMISSION("Falha por omissão."),
    CRASH("Falha por crash."),
    ERROR("Falha genérica de erro."),
    TIME("Falha por timeout.");

    private final String description;

    FailType(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
}
