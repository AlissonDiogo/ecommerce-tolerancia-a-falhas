package br.ufrn.imd.ecommerce.fails;

public class Fail extends RuntimeException  {
    private EnumFailType failType;

    public Fail(EnumFailType failType) {
        super("Falha do tipo: " + failType);
        this.failType = failType;
    }

    public EnumFailType getFailType() {
        return failType;
    }
}
