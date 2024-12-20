package br.ufrn.imd.ecommerce.utils.tasks;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

import br.ufrn.imd.ecommerce.dto.FidelityRequestDto;
import br.ufrn.imd.ecommerce.service.fidelity.FidelityService;
import br.ufrn.imd.ecommerce.service.fidelity.FidelityServiceImpl;
import br.ufrn.imd.ecommerce.utils.fails.Fail;

@Service
public class ProcessBonusImpl implements ProcessBonus {
    private final Logger logger = LoggerFactory.getLogger(ProcessBonusImpl.class);

    private final FidelityService fidelityService;

    private final Queue<FidelityRequestDto> bonusProcessingQueue = new ConcurrentLinkedQueue<>();

    public ProcessBonusImpl() {
        this.fidelityService = new FidelityServiceImpl("http://192.168.0.10:8084");
    }

    @Scheduled(fixedRate = 30000)
    public void retryProcessBonus() {
        logger.info("Iniciando processamento de bonus");
        while (!this.bonusProcessingQueue.isEmpty()) {
            FidelityRequestDto requestFidelity = this.bonusProcessingQueue.poll();
            try {
                this.fidelityService.bonus(requestFidelity);
                logger.info("Bonus do usuario {} processado", requestFidelity.idUser());
            } catch (Fail f) {
                logger.error("Falha ao processar bonus do usuario {}", requestFidelity.idUser());
                this.addBonusToProcessLater(requestFidelity);
                break;
            }
        }
    }

    public void addBonusToProcessLater(FidelityRequestDto fidelity) {
        this.bonusProcessingQueue.offer(fidelity);
    }
}
