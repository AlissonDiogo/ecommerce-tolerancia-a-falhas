package br.ufrn.imd.ecommerce.controller;

import br.ufrn.imd.ecommerce.dto.BuyRequestDto;
import br.ufrn.imd.ecommerce.dto.BuyResponseDto;
import br.ufrn.imd.ecommerce.service.EcommerceProcessor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/e-commerce")
public class ECommerceController {


    @PostMapping("/buy")
    public ResponseEntity<?> buy(@RequestBody BuyRequestDto buyRequestDto) {
        EcommerceProcessor processor = new EcommerceProcessor();
        try {
            BuyResponseDto buyResponseDto = processor.processBuy(buyRequestDto);
            return ResponseEntity.ok(buyResponseDto);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
