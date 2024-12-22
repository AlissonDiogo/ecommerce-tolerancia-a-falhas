package br.ufrn.imd.store.controller;

import br.ufrn.imd.store.model.Product;
import br.ufrn.imd.store.dto.SellRequestDto;
import br.ufrn.imd.store.dto.SellResponseDto;
import br.ufrn.imd.store.failure.HandleFailure;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/store")
public class StoreController {

    private final Logger logger = LoggerFactory.getLogger(StoreController.class);

    @Autowired
    private final HandleFailure handleFailure;

    public StoreController(HandleFailure handleFailure) {
        this.handleFailure = handleFailure;
    }

    @GetMapping("/product")
    public CompletableFuture<ResponseEntity<Product>> consultProduct(@NonNull @RequestParam("productId") String productId) {
        Product product = new Product(UUID.fromString(productId), "Produto X", 20.0);

        if (handleFailure.isFailureOccurring(0.2f)) {
            return CompletableFuture.supplyAsync(() -> {
                try {
                    Thread.sleep(4000);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                    throw new RuntimeException("Erro durante o atraso", e);
                }
                return ResponseEntity.ok(product);
            });
        } else {
            return CompletableFuture.completedFuture(new ResponseEntity<>(product, HttpStatus.OK));
        }
    }

    @PostMapping("/sell")
    public ResponseEntity<SellResponseDto> checkSellByProduct(@NonNull @RequestBody SellRequestDto sellRequestDto) {

        if (handleFailure.checkFailureActivation("sell")) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if (handleFailure.isFailureOccurring(0.1f)) {
            handleFailure.activeFailure("sell", 5);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        SellResponseDto sell = new SellResponseDto(UUID.randomUUID());

        logger.info("Para a venda do produto de id {}, o identificador da transação é {}.", sellRequestDto.productId(), sell.transactionId());

        return new ResponseEntity<>(sell, HttpStatus.OK);
    }

}
