package br.ufrn.imd.ecommerce.service.fidelity;

import br.ufrn.imd.ecommerce.dto.FidelityRequestDto;
import org.springframework.scheduling.annotation.Scheduled;

import java.util.Queue;
import java.util.concurrent.ConcurrentLinkedQueue;

public interface FidelityFaultToleranceStrategy {

    Queue<FidelityRequestDto> bonusProcessingQueue = new ConcurrentLinkedQueue<>();

    @Scheduled(fixedRate = 30000)
    void retryProcessBonus();

    default void addBonusToProcessLater(FidelityRequestDto fidelity) {
        bonusProcessingQueue.offer(fidelity);
    }
}
