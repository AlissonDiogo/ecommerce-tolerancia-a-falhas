package br.ufrn.imd.ecommerce.service.fidelity;

import br.ufrn.imd.ecommerce.dto.FidelityRequestDto;
import br.ufrn.imd.ecommerce.utils.client.RestClient;
import br.ufrn.imd.ecommerce.utils.fails.Fail;
import br.ufrn.imd.ecommerce.utils.fails.FailType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.io.IOException;
import java.net.http.HttpResponse;

public class FidelityProcess implements FidelityService, FidelityFaultToleranceStrategy {

    private final Logger logger = LoggerFactory.getLogger(FidelityProcess.class);

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

    @Override
    @Scheduled(fixedRate = 30000)
    public void retryProcessBonus() {
        logger.info("Iniciando processamento de bonus");
        while (!bonusProcessingQueue.isEmpty()) {
            FidelityRequestDto requestFidelity = bonusProcessingQueue.poll();
            try {
                addBonus(requestFidelity);
                logger.info("Bonus do usuario {} processado!", requestFidelity.idUser());
            } catch (Fail f) {
                logger.error("Falha ao processar bonus do usuario {}.", requestFidelity.idUser());
                break;
            }
        }
    }
}
