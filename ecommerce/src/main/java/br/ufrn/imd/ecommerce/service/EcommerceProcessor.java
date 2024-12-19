package br.ufrn.imd.ecommerce.service;

import br.ufrn.imd.ecommerce.dto.*;
import br.ufrn.imd.ecommerce.utils.fails.Fail;
import br.ufrn.imd.ecommerce.service.exchange.ExchangeImpl;
import br.ufrn.imd.ecommerce.service.fidelity.FidelityService;
import br.ufrn.imd.ecommerce.service.fidelity.FidelityServiceImpl;
import br.ufrn.imd.ecommerce.service.store.StoreService;
import br.ufrn.imd.ecommerce.service.store.StoreServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class EcommerceProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(EcommerceProcessor.class);

    private final StoreService storeService;
    private final ExchangeImpl exchangeService;
    private final FidelityService fidelityService;

    public EcommerceProcessor() {
        this.storeService = new StoreServiceImpl("http://192.168.1.212:8082");
        this.exchangeService = new ExchangeImpl("http://192.168.1.212:8083");
        this.fidelityService = new FidelityServiceImpl("http://192.168.1.212:8084");
    }

    @Override
    public BuyResponseDto processBuy(BuyRequestDto requestDto) {
        // REQUEST 01
        ProductResponseDto productResponseDto = this.storeService.checkProductById(requestDto.productId());
        System.out.println(productResponseDto.productId());

        // REQUEST 02
        try {
            ExchangeResponseDto exchangeResponseDto = this.exchangeService.consultExchange();
            this.exchangeService.updateExchange(exchangeResponseDto.value());
        } catch (Fail f) {
            logger.error("[EXCHANGE] {} Será usado o valor obtido na última consulta.", f.getMessage());
        }
        logger.info("O valor do câmbio é R$ {}", exchangeService.getLastExchangeValue());

        // REQUEST 03
        UUID transactionId = this.storeService.sellProduct(new SellRequestDto(requestDto.productId()));
        System.out.println(transactionId);

        // REQUEST 04
        FidelityRequestDto fidelityRequestDto = new FidelityRequestDto(requestDto.userId(), 30d);
        int statusCode = this.fidelityService.bonus(fidelityRequestDto);
        System.out.println(statusCode);

        return new BuyResponseDto(HttpStatus.CREATED, transactionId);
    }
}
