package br.ufrn.imd.ecommerce.service.exchange;

public interface ExchangeFaultToleranceStrategy {

    Double getLastExchangeValue();

    void updateExchange(Double value);

}
