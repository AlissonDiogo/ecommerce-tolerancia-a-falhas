package br.ufrn.imd.ecommerce.service.store;

import br.ufrn.imd.ecommerce.dto.SellRequestDto;
import br.ufrn.imd.ecommerce.utils.client.RestClient;
import br.ufrn.imd.ecommerce.dto.ProductResponseDto;
import br.ufrn.imd.ecommerce.utils.fails.Fail;
import br.ufrn.imd.ecommerce.utils.fails.FailType;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.http.HttpResponse;
import java.util.UUID;

public class StoreServiceImpl implements StoreService {

    private final RestClient restClient;

    public StoreServiceImpl(String address) {
        this.restClient = new RestClient(address);
    }

    @Override
    public ProductResponseDto checkProductById(String productId) throws Fail {
        try{
            return restClient.get("/store/product?productId="+productId, ProductResponseDto.class);
        } catch (IOException | InterruptedException e) {
            throw new Fail(FailType.OMISSION);
        }
    }

    public ProductResponseDto retryCheckProductById(String productId) throws Fail {
        int retryStep = 0;
        while (retryStep < 3) {
            try {
                return checkProductById(productId);
            } catch (Fail e) {
                if (e.getFailType() == FailType.OMISSION) {
                    retryStep++;
                    System.out.println("Tentando novamente checkProductById: " + retryStep);
                } else {
                    throw e;
                }
            }
        } 
        throw new Fail(FailType.OMISSION);
    }

    @Override
    public UUID sellProduct(SellRequestDto requestDto) throws Fail {
        record SellResponseDto(UUID transactionId){}

        try{
            HttpResponse<String> response = restClient.post("/store/sell", requestDto);
            SellResponseDto responseDto = new ObjectMapper().readValue(response.body(), SellResponseDto.class);

            return responseDto.transactionId();
        }catch (IOException | InterruptedException f) {
            throw new Fail(FailType.ERROR);
        }
    }


}
