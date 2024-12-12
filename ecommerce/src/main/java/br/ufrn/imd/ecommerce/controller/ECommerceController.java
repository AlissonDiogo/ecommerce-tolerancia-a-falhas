package br.ufrn.imd.ecommerce.controller;

import br.ufrn.imd.ecommerce.dto.BuyRequestDto;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/e-commerce")
public class ECommerceController {

    @PostMapping("/buy")
    public String buy(@RequestBody BuyRequestDto buyRequestDto) {
        return buyRequestDto.toString();
    }
}
