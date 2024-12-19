package br.ufrn.imd.ecommerce.service.fidelity;

import br.ufrn.imd.ecommerce.utils.client.RestClient;
import br.ufrn.imd.ecommerce.dto.FidelityRequestDto;

import java.io.IOException;
import java.net.http.HttpResponse;

public class FidelityServiceImpl implements FidelityService {

    private final RestClient restClient;

    public FidelityServiceImpl(String address) {
        this.restClient = new RestClient(address);
    }

    @Override
    public int bonus(FidelityRequestDto fidelity) {
        try{
            HttpResponse<String> response =  restClient.post("/bonus", fidelity);
            return response.statusCode();
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException("Erro no processamento do bonus: " + e.getMessage());
        }
    }
}
