package br.ufrn.imd.ecommerce.service;

import br.ufrn.imd.ecommerce.dto.*;
import br.ufrn.imd.ecommerce.fails.Fail;
import br.ufrn.imd.ecommerce.service.exchange.ExchangeService;
import br.ufrn.imd.ecommerce.service.exchange.ExchangeServiceImpl;
import br.ufrn.imd.ecommerce.service.fidelity.FidelityService;
import br.ufrn.imd.ecommerce.service.fidelity.FidelityServiceImpl;
import br.ufrn.imd.ecommerce.service.store.StoreService;
import br.ufrn.imd.ecommerce.service.store.StoreServiceImpl;
import org.springframework.http.HttpStatus;

import com.fasterxml.jackson.databind.node.DoubleNode;

public class EcommerceProcessor implements Processor {

    private StoreService storeService;
    private ExchangeService exchangeService;
    private FidelityService fidelityService;

    public EcommerceProcessor() {
        this.storeService = new StoreServiceImpl("http://192.168.1.212:8082");
        this.exchangeService = new ExchangeServiceImpl("http://192.168.1.212:8083");
        this.fidelityService = new FidelityServiceImpl("http://192.168.1.212:8084");
    }

    @Override
    public BuyResponseDto processBuy(BuyRequestDto requestDto) {

        // REQUEST 01
        ProductResponseDto productResponseDto = this.storeService.checkProductById(requestDto.productId());
        System.out.println(productResponseDto.productId());

        // REQUEST 02
        Double exchangeValue = ExchangeServiceImpl.value;
        try {
            ExchangeResponseDto exchangeResponseDto = this.exchangeService.consultExchange();
            ExchangeServiceImpl.value = exchangeResponseDto.value();
            exchangeValue = exchangeResponseDto.value();
        } catch (Fail f) {
            System.out.println("Falha do tipo: " + f.getFailType()
                    + " no microserviço de exchange. Será usado o valor obtido na última consulta.");
        }
        System.out.println(exchangeValue);

        // REQUEST 03
        FidelityRequestDto fidelityRequestDto = new FidelityRequestDto(requestDto.userId(), 30d);
        int statusCode = this.fidelityService.bonus(fidelityRequestDto);
        System.out.println(statusCode);

        return new BuyResponseDto(HttpStatus.CREATED, 10);
    }
}
