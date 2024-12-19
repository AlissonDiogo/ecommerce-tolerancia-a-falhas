package br.ufrn.imd.ecommerce.utils.fails;

public class Fail extends RuntimeException  {
    private FailType failType;

    public Fail(FailType failType) {
        super(failType.getDescription());
        this.failType = failType;
    }

    public FailType getFailType() {
        return failType;
    }
}
