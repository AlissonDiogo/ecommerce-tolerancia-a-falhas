package br.ufrn.imd.ecommerce.service;

import br.ufrn.imd.ecommerce.dto.*;
import br.ufrn.imd.ecommerce.utils.fails.Fail;
import br.ufrn.imd.ecommerce.utils.tasks.ProcessBonus;
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

    private final ProcessBonus processBonus;

    public EcommerceProcessor(ProcessBonus processBonus) {
        this.storeService = new StoreServiceImpl("http://192.168.0.10:8082");
        this.exchangeService = new ExchangeImpl("http://192.168.0.10:8083");
        this.fidelityService = new FidelityServiceImpl("http://192.168.0.10:8084");
        this.processBonus = processBonus;
    }

    @Override
    public BuyResponseDto processBuy(BuyRequestDto requestDto) {
        // REQUEST 01
        Double productValue = 0.0d;
        try {
            ProductResponseDto productResponseDto = this.storeService.checkProductById(requestDto.productId());
            productValue = productResponseDto.value();
            logger.info("[STORE] Endpoint /product efetuado com sucesso o produto de id {}",
                    productResponseDto.productId());
        } catch (Fail f) {
            if (requestDto.ft()) {
                try {
                    ProductResponseDto productResponseDto = this.storeService
                            .retryCheckProductById(requestDto.productId());
                    productValue = productResponseDto.value();
                    logger.info("[STORE] Endpoint /product efetuado com sucesso o produto de id {}",
                            productResponseDto.productId());
                } catch (Fail f2) {
                    throw f2;
                }
            } else {
                logger.error("[STORE] Tolerância a falhas desativado -> endpoint /product");
                throw f;
            }
        }

        // REQUEST 02
        try {
            ExchangeResponseDto exchangeResponseDto = this.exchangeService.consultExchange();
            this.exchangeService.updateExchange(exchangeResponseDto.value());
        } catch (Fail f) {
            if (requestDto.ft()) {
                logger.error("[EXCHANGE] {} Será usado o valor obtido na última consulta.",
                        f.getMessage());
            } else {
                logger.error("[EXCHANGE] Tolerância a falhas desabilitado.");
                throw f;
            }
        }
        logger.info("O valor do câmbio é R$ {}", exchangeService.getLastExchangeValue());

        // REQUEST 03
        UUID transactionId = this.storeService.sellProduct(new SellRequestDto(requestDto.productId()));
        System.out.println(transactionId);

        // REQUEST 04
        FidelityRequestDto fidelityRequestDto = new FidelityRequestDto(requestDto.userId(),
                (int) Math.round(productValue));
        try {
            this.fidelityService.bonus(fidelityRequestDto);
        } catch (Fail f) {
            if (requestDto.ft()) {
                logger.error("[FIDELITY] {} O processamento ocorrera mais tarde.", f.getMessage());
                this.processBonus.addBonusToProcessLater(fidelityRequestDto);
            } else {
                logger.error("[EXCHANGE] Tolerância a falhas desabilitado.");
                throw f;
            }
        }

        return new BuyResponseDto(HttpStatus.CREATED, transactionId);
    }
}
