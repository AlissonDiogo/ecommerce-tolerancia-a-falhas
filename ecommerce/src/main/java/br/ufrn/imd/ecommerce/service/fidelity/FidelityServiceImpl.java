package br.ufrn.imd.ecommerce.service.fidelity;

import br.ufrn.imd.ecommerce.client.WebClient;
import br.ufrn.imd.ecommerce.dto.FidelityRequestDto;

import java.io.IOException;
import java.net.http.HttpResponse;

public class FidelityServiceImpl implements FidelityService {

    private final WebClient webClient;

    public FidelityServiceImpl(String address) {
        this.webClient = new WebClient(address);
    }

    @Override
    public int bonus(FidelityRequestDto fidelity) {
        try{
            HttpResponse<String> response =  webClient.post("/bonus", fidelity);
            return response.statusCode();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro no processamento do bonus: " + e.getMessage());
        }
    }
}
