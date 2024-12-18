package br.ufrn.imd.ecommerce.service;

import br.ufrn.imd.ecommerce.dto.BuyRequestDto;
import br.ufrn.imd.ecommerce.dto.BuyResponseDto;

public interface Processor {

    BuyResponseDto processBuy(BuyRequestDto requestDto);

}
