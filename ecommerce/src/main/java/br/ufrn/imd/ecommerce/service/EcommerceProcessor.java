package br.ufrn.imd.ecommerce.service;

import br.ufrn.imd.ecommerce.dto.*;
import br.ufrn.imd.ecommerce.service.exchange.ExchangeService;
import br.ufrn.imd.ecommerce.service.exchange.ExchangeServiceImpl;
import br.ufrn.imd.ecommerce.service.fidelity.FidelityService;
import br.ufrn.imd.ecommerce.service.fidelity.FidelityServiceImpl;
import br.ufrn.imd.ecommerce.service.store.StoreService;
import br.ufrn.imd.ecommerce.service.store.StoreServiceImpl;
import org.springframework.http.HttpStatus;

public class EcommerceProcessor implements Processor {

    private StoreService storeService;
    private ExchangeService exchangeService;
    private FidelityService fidelityService;

    public EcommerceProcessor() {
        this.storeService = new StoreServiceImpl("http://192.168.1.97:8083");
        this.exchangeService = new ExchangeServiceImpl("http://192.168.1.97:8081");
        this.fidelityService = new FidelityServiceImpl("http://192.168.1.97:8085");
    }

    @Override
    public BuyResponseDto processBuy(BuyRequestDto requestDto) {

        // REQUEST 01
        ProductResponseDto productResponseDto = this.storeService.checkProductById(requestDto.productId());
        System.out.println(productResponseDto.productId());

        // REQUEST 02
        ExchangeResponseDto exchangeResponseDto = this.exchangeService.consultExchange();
        System.out.println(exchangeResponseDto.value());

        // REQUEST 03
        FidelityRequestDto fidelityRequestDto = new FidelityRequestDto(requestDto.userId(), 30d);
        this.fidelityService.bonus(fidelityRequestDto);



        return new BuyResponseDto(HttpStatus.CREATED, 10);
    }
}
