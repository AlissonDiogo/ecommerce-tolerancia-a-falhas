package br.ufrn.imd.ecommerce.service.fidelity;

import br.ufrn.imd.ecommerce.dto.FidelityRequestDto;
import io.netty.handler.codec.http.HttpResponseStatus;

public interface FidelityService {

    HttpResponseStatus bonus(FidelityRequestDto requestDto);

}
