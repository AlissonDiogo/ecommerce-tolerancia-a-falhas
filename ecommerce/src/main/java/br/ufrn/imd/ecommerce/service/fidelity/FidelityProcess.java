package br.ufrn.imd.ecommerce.service.fidelity;

import br.ufrn.imd.ecommerce.dto.FidelityRequestDto;
import br.ufrn.imd.ecommerce.utils.client.RestClient;
import br.ufrn.imd.ecommerce.utils.fails.Fail;
import br.ufrn.imd.ecommerce.utils.fails.FailType;

import java.io.IOException;
import java.net.http.HttpResponse;


public class FidelityProcess implements FidelityService {


    private final RestClient restClient;

    public FidelityProcess(String address) {
        this.restClient = new RestClient(address);
    }

    @Override
    public int addBonus(FidelityRequestDto requestFidelity) {
        try {
            HttpResponse<String> response = restClient.post("/bonus", requestFidelity);
            return response.statusCode();
        } catch (IOException | InterruptedException e) {
            throw new Fail(FailType.TIME);
        }
    }
}
