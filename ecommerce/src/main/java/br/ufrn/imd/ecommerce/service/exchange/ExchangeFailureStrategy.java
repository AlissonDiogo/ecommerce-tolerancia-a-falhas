package br.ufrn.imd.ecommerce.service.exchange;

public interface ExchangeFailureStrategy {

    Double getLastExchangeValue();

    void updateExchange(Double value);

}
