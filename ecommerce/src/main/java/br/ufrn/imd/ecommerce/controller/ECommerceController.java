package br.ufrn.imd.ecommerce.controller;

import br.ufrn.imd.ecommerce.dto.BuyRequestDto;
import org.springframework.web.bind.annotation.*;
import br.ufrn.imd.ecommerce.client.EcommerceWebClient;
import reactor.core.publisher.Mono;


@RestController
@RequestMapping("/e-commerce")
public class ECommerceController {

    private final EcommerceWebClient clientExchange = new EcommerceWebClient("http://192.168.1.212:", "8081");

    @PostMapping("/buy")
    public String buy(@RequestBody BuyRequestDto buyRequestDto) {
        String teste = clientExchange.send("/exchange");
        System.out.println(teste);
        return teste; 
    }
}
