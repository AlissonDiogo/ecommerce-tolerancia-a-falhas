package br.ufrn.imd.ecommerce.service.exchange;

import br.ufrn.imd.ecommerce.client.WebClient;
import br.ufrn.imd.ecommerce.dto.ExchangeResponseDto;

import java.io.IOException;

public class ExchangeServiceImpl implements ExchangeService {

    private final WebClient webClient;

    public ExchangeServiceImpl(String address) {
        this.webClient = new WebClient(address);
    }

    @Override
    public ExchangeResponseDto consultExchange() {
        try{
            return webClient.get("/exchange", ExchangeResponseDto.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro no processamento da consulta de intercâmbio produto: " + e.getMessage());
        }
    }
}
