package br.ufrn.imd.ecommerce.service;

import br.ufrn.imd.ecommerce.dto.*;
import br.ufrn.imd.ecommerce.model.Product;
import br.ufrn.imd.ecommerce.service.exchange.ExchangeProcess;
import br.ufrn.imd.ecommerce.service.fidelity.FidelityProcess;
import br.ufrn.imd.ecommerce.service.store.StoreProcess;
import br.ufrn.imd.ecommerce.service.store.async.AsyncStoreProcessor;
import br.ufrn.imd.ecommerce.utils.fails.Fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;

import java.util.UUID;

public class EcommerceProcessor implements Processor {

    private final Logger logger = LoggerFactory.getLogger(EcommerceProcessor.class);

    private final StoreProcess storeProcess;
    private final ExchangeProcess exchangeProcess;
    private final FidelityProcess fidelityProcess;

    private final AsyncStoreProcessor asyncStoreProcessor;

    public EcommerceProcessor() {
        this.storeProcess = new StoreProcess("http://192.168.0.101:8082");
        this.exchangeProcess = new ExchangeProcess("http://192.168.0.101:8083");
        this.fidelityProcess = new FidelityProcess("http://192.168.0.101:8084");

        this.asyncStoreProcessor = new AsyncStoreProcessor(storeProcess);
    }

    @Override
    public BuyResponseDto processBuy(BuyRequestDto requestDto) throws RuntimeException {
        // REQUEST 01
        Product product;
        try {
            product = this.storeProcess.checkProductById(requestDto.productId());
        } catch (Fail f) {
            if (requestDto.ft()) {
                product = this.storeProcess.retryCheckProductById(requestDto.productId());
                logger.info("[STORE] Endpoint /product efetuado com sucesso o produto de id {}", product.id());
            } else {
                throw new RuntimeException("[STORE] Tolerância a falhas desativada: "+f.getMessage());
            }
        }

        // REQUEST 02
        try {
            ExchangeResponseDto exchangeResponseDto = this.exchangeProcess.consultExchange();
            this.exchangeProcess.updateExchange(exchangeResponseDto.value());
        } catch (Fail f) {
            if (requestDto.ft()) {
                logger.info("[EXCHANGE] Será usado o valor obtido na última consulta. Cujo valor é {}", this.exchangeProcess.getLastExchangeValue());
            } else {
                throw new RuntimeException("[EXCHANGE] Tolerância a falhas desativada: "+f.getMessage());
            }
        }

        // REQUEST 03
        UUID transactionId = null;
        SellRequestDto sellRequestDto = new SellRequestDto(requestDto.productId());
        try{
            transactionId = this.storeProcess.sellProduct(sellRequestDto);
        }catch (Fail f){
            if (requestDto.ft()) {
                asyncStoreProcessor.enqueueSellTask(sellRequestDto);
            } else {
                throw new RuntimeException("[STORE] Tolerância a falhas desativada: "+f.getMessage());
            }
        }


        // REQUEST 04
        FidelityRequestDto fidelityRequestDto = new FidelityRequestDto(requestDto.userId(), (int) Math.round(product.value()));
        try {
            int statusCode = this.fidelityProcess.addBonus(fidelityRequestDto);
            logger.info("O status code da requisição foi {}.", statusCode);
        } catch (Fail f) {
            if (requestDto.ft()) {
                logger.info("[FIDELITY] {}. O processamento ocorrerá mais tarde.", f.getMessage());
                this.fidelityProcess.addBonusToProcessLater(fidelityRequestDto);
            } else {
                throw new RuntimeException("[FIDELITY] Tolerância a falhas desativada: {}"+f.getMessage());
            }
        }

        return new BuyResponseDto(HttpStatus.CREATED, transactionId);
    }
}
