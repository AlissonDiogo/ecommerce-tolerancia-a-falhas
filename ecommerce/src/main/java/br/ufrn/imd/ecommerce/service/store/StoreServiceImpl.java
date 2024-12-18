package br.ufrn.imd.ecommerce.service.store;

import br.ufrn.imd.ecommerce.client.WebClient;
import br.ufrn.imd.ecommerce.dto.ProductResponseDto;

import java.io.IOException;

public class StoreServiceImpl implements StoreService {

    private final WebClient webClient;

    public StoreServiceImpl(String address) {
        this.webClient = new WebClient(address);
    }

    @Override
    public ProductResponseDto checkProductById(String productId) {
        try{
            return webClient.get("/store/product?productId="+productId, ProductResponseDto.class);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro no processamento da consulta do produto: " + e.getMessage());
        }
    }
}
