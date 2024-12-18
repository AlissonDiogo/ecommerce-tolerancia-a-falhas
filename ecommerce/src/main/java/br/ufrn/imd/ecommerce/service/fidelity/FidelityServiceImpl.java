package br.ufrn.imd.ecommerce.service.fidelity;

import br.ufrn.imd.ecommerce.client.WebClient;
import br.ufrn.imd.ecommerce.dto.FidelityRequestDto;
import io.netty.handler.codec.http.HttpResponseStatus;

import java.io.IOException;

public class FidelityServiceImpl implements FidelityService {

    private final WebClient webClient;

    public FidelityServiceImpl(String address) {
        this.webClient = new WebClient(address);
    }

    @Override
    public HttpResponseStatus bonus(FidelityRequestDto fidelity) {
        try{
            return (HttpResponseStatus) webClient.post("/bonus", fidelity);
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro no processamento do bonus: " + e.getMessage());
        }
    }
}
