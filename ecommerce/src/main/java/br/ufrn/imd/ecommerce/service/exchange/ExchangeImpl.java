package br.ufrn.imd.ecommerce.service.exchange;

import br.ufrn.imd.ecommerce.utils.client.RestClient;
import br.ufrn.imd.ecommerce.dto.ExchangeResponseDto;
import br.ufrn.imd.ecommerce.utils.fails.FailType;
import br.ufrn.imd.ecommerce.utils.fails.Fail;

import java.io.IOException;

public class ExchangeImpl implements ExchangeService, ExchangeFailureStrategy {

    private final RestClient restClient;
    private Double exchangeValue;

    public ExchangeImpl(String address) {
        this.restClient = new RestClient(address);
        this.exchangeValue = 0d;
    }

    @Override
    public void updateExchange(Double newValue) {
        this.exchangeValue = newValue;
    }

    @Override
    public ExchangeResponseDto consultExchange() {
        try{
            return restClient.get("/exchange", ExchangeResponseDto.class);
        } catch (IOException | InterruptedException e) {
            throw new Fail(FailType.CRASH);
        }
    }

    @Override
    public Double getLastExchangeValue() {
        return this.exchangeValue;
    }
}
