package br.ufrn.imd.store.controller;

import br.ufrn.imd.store.dto.ProductResponseDto;
import br.ufrn.imd.store.dto.SellRequestDto;
import br.ufrn.imd.store.dto.SellResponseDto;
import br.ufrn.imd.store.failure.HandleFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/store")
public class StoreController {

    @Autowired
    private final HandleFailure handleFailure;

    public StoreController(HandleFailure handleFailure) {
        this.handleFailure = handleFailure;
    }

    @GetMapping("/product")
    public ResponseEntity<ProductResponseDto> consultProduct(@NonNull @RequestParam("productId") String productId) {
        if(handleFailure.isFailureOccurring(0.2f)){
            return new ResponseEntity<>(HttpStatus.SERVICE_UNAVAILABLE);
        }

        ProductResponseDto product = new ProductResponseDto(productId, "Produto X", 20.0);
        return new ResponseEntity<>(product, HttpStatus.OK);
    }

    @PostMapping("/sell")
    public ResponseEntity<SellResponseDto> checkSellByProduct(@NonNull @RequestBody SellRequestDto sellRequestDto) {
        System.out.println("Processando venda do produto de id: "+sellRequestDto.productId());

        if(handleFailure.checkFailureActivation("sell")) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(handleFailure.isFailureOccurring(0.1f)){
            handleFailure.activeFailure("sell", 5);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        SellResponseDto sell = new SellResponseDto(UUID.randomUUID());
        return new ResponseEntity<>(sell, HttpStatus.OK);
    }


}
