package br.ufrn.imd.ecommerce.service.exchange;

import br.ufrn.imd.ecommerce.client.WebClient;
import br.ufrn.imd.ecommerce.dto.ExchangeResponseDto;
import br.ufrn.imd.ecommerce.fails.EnumFailType;
import br.ufrn.imd.ecommerce.fails.Fail;

import java.io.IOException;

public class ExchangeServiceImpl implements ExchangeService {

    private final WebClient webClient;
    public static Double value;

    public ExchangeServiceImpl(String address) {
        this.webClient = new WebClient(address);
    }

    @Override
    public ExchangeResponseDto consultExchange() {
        try{
            return webClient.get("/exchange", ExchangeResponseDto.class);
        } catch (IOException | InterruptedException e) {
            throw new Fail(EnumFailType.CRASH);
        }
    }
}
