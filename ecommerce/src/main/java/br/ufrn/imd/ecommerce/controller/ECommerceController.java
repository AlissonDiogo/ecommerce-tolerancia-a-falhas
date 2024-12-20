package br.ufrn.imd.ecommerce.controller;

import br.ufrn.imd.ecommerce.dto.BuyRequestDto;
import br.ufrn.imd.ecommerce.dto.BuyResponseDto;
import br.ufrn.imd.ecommerce.service.EcommerceProcessor;
import br.ufrn.imd.ecommerce.utils.tasks.ProcessBonus;

import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/e-commerce")
public class ECommerceController {
    private final ProcessBonus processBonus;

    public ECommerceController(ProcessBonus processBonus) {
        this.processBonus = processBonus;
    }

    @PostMapping("/buy")
    public String buy(@RequestBody BuyRequestDto buyRequestDto) {
        EcommerceProcessor processor = new EcommerceProcessor(this.processBonus);
        BuyResponseDto buyResponseDto = processor.processBuy(buyRequestDto);

        return buyResponseDto.toString();
    }
}
