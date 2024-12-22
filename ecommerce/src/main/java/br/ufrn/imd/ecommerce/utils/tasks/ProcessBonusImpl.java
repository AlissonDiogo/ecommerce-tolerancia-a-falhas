package br.ufrn.imd.ecommerce.utils.tasks;

import br.ufrn.imd.ecommerce.dto.FidelityRequestDto;
import br.ufrn.imd.ecommerce.service.fidelity.FidelityProcess;
import br.ufrn.imd.ecommerce.service.fidelity.FidelityService;
import br.ufrn.imd.ecommerce.utils.fails.Fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

@Service
public class ProcessBonusImpl implements ProcessBonus {
    private final Logger logger = LoggerFactory.getLogger(ProcessBonusImpl.class);

    private final FidelityService fidelityService;

    private final Queue<FidelityRequestDto> bonusProcessingQueue;

    public ProcessBonusImpl() {
        this.bonusProcessingQueue = new ConcurrentLinkedQueue<>();
        this.fidelityService = new FidelityProcess("http://192.168.0.101:8084");
    }

    @Scheduled(fixedRate = 30000)
    public void retryProcessBonus() {
        while (!this.bonusProcessingQueue.isEmpty()) {
            FidelityRequestDto requestFidelity = this.bonusProcessingQueue.peek();
            String idUser = requestFidelity.idUser();
            logger.info("Iniciando processamento do bonus do usuario {}", idUser);
            try {
                this.fidelityService.addBonus(requestFidelity);
                logger.info("Bonus do usuario {} processado", idUser);
                this.bonusProcessingQueue.poll();
            } catch (Fail f) {
                logger.error("Falha ao processar bonus do usuario {}", idUser);
                break;
            }
        }
    }

    public void addBonusToProcessLater(FidelityRequestDto fidelity) {
        this.bonusProcessingQueue.offer(fidelity);
    }
}
