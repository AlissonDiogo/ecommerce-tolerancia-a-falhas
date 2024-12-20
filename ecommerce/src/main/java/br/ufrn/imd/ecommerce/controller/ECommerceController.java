package br.ufrn.imd.ecommerce.controller;

import br.ufrn.imd.ecommerce.dto.BuyRequestDto;
import br.ufrn.imd.ecommerce.dto.BuyResponseDto;
import br.ufrn.imd.ecommerce.service.EcommerceProcessor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/e-commerce")
public class ECommerceController {

    @PostMapping("/buy")
    public String buy(@RequestBody BuyRequestDto buyRequestDto) {
        EcommerceProcessor processor = new EcommerceProcessor();
        try { 
            BuyResponseDto buyResponseDto = processor.processBuy(buyRequestDto);
            return buyResponseDto.toString();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return e.getMessage();
        }
    }
}
