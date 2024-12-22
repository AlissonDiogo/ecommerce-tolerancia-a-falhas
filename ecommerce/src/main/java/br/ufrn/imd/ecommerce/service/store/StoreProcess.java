package br.ufrn.imd.ecommerce.service.store;

import br.ufrn.imd.ecommerce.dto.SellRequestDto;
import br.ufrn.imd.ecommerce.dto.SellResponseDto;
import br.ufrn.imd.ecommerce.model.Product;
import br.ufrn.imd.ecommerce.utils.client.RestClient;
import br.ufrn.imd.ecommerce.utils.fails.Fail;
import br.ufrn.imd.ecommerce.utils.fails.FailType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.UUID;


public class StoreProcess implements StoreService, StoreFaultToleranceStrategy {

    private final Logger logger = LoggerFactory.getLogger(StoreProcess.class);

    private final RestClient restClient;

    public StoreProcess(String address) {
        this.restClient = new RestClient(address);
    }

    @Override
    public Product checkProductById(UUID productId) throws Fail {
        logger.info("[STORE] Processando busca de produto com id: {}", productId);

        try{
            return restClient.get("/store/product?productId="+productId, Product.class);
        } catch (IOException | InterruptedException e) {
            throw new Fail(FailType.OMISSION);
        }
    }

    @Override
    public UUID sellProduct(SellRequestDto requestDto) throws Fail {
        logger.info("[STORE] Processando compra de produto com id: {}", requestDto.productId());

        try{
            HttpResponse<String> response = restClient.post("/store/sell", requestDto);
            SellResponseDto responseDto = new ObjectMapper().readValue(response.body(), SellResponseDto.class);

            return responseDto.transactionId();
        }catch (IOException | InterruptedException f) {
            throw new Fail(FailType.ERROR);
        }
    }


    @Override
    public Product retryCheckProductById(UUID productId) throws Fail {
        logger.info("Iniciando o processo de recuperação...");

        for(int attempt = 1; attempt <= 3; attempt++) {
            try {
                Thread.sleep(500);
                return checkProductById(productId);
            }catch (Fail f) {
                logger.warn("[STORE] Erro no retry {} da consulta do produto com id: {} ", attempt, productId);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        logger.error("[STORE] Não foi possível realizar a consulta mesmo após as 3 tentativas.");
        throw new Fail(FailType.OMISSION);
    }

    @Override
    public UUID retrySellProduct(SellRequestDto requestDto) {
        for(int attempt = 1; attempt <= 3; attempt++) {
            try {
                Thread.sleep(500);
                return sellProduct(requestDto);
            }catch (Fail f) {
                logger.warn("[STORE] Erro no retry {} do processo de compra do produto com id: {} ", attempt, requestDto.productId());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        logger.error("[STORE] Não foi possível finalizae a compra mesmo após as 3 tentativas.");
        return null;
    }


}
