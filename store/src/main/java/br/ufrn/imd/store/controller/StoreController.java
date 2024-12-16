package br.ufrn.imd.store.controller;

import br.ufrn.imd.store.dto.ProductResponseDto;
import br.ufrn.imd.store.dto.SellResponseDto;
import br.ufrn.imd.store.failure.HandleFailure;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.lang.NonNull;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @GetMapping("/sell")
    public ResponseEntity<SellResponseDto> checkSellByProduct(@NonNull @RequestParam("productId") String productId) {
        if(handleFailure.checkFailureActivation("sell")) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        if(handleFailure.isFailureOccurring(0.1f)){
            handleFailure.activeFailure("sell", 5);
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }

        SellResponseDto sell = new SellResponseDto("sell-".concat(productId));
        return new ResponseEntity<>(sell, HttpStatus.OK);
    }


}