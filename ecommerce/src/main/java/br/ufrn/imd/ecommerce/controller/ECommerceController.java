package br.ufrn.imd.ecommerce.controller;

import br.ufrn.imd.ecommerce.dto.BuyRequestDto;
import br.ufrn.imd.ecommerce.service.EcommerceProcessor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/e-commerce")
public class ECommerceController {

    @PostMapping("/buy")
    public String buy(@RequestBody BuyRequestDto buyRequestDto) {
        EcommerceProcessor processor = new EcommerceProcessor();
        processor.processBuy(buyRequestDto);

        return "teste";
    }
}
