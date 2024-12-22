package br.ufrn.imd.ecommerce.service.store.async;

import br.ufrn.imd.ecommerce.dto.SellRequestDto;
import br.ufrn.imd.ecommerce.service.store.StoreProcess;
import br.ufrn.imd.ecommerce.utils.fails.Fail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.UUID;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import static java.lang.Thread.sleep;

public class AsyncStoreProcessor implements Runnable {

    private final Logger logger = LoggerFactory.getLogger(AsyncStoreProcessor.class);
    private final BlockingQueue<SellRequestDto> taskQueue = new LinkedBlockingQueue<>();
    private final StoreProcess storeProcess;

    public AsyncStoreProcessor(StoreProcess storeProcess) {
        this.storeProcess = storeProcess;

        new Thread(this).start();
    }

    public void enqueueSellTask(SellRequestDto sellRequestDto) {
        logger.info("Enfileirando tarefa de venda para o produto ID: {}", sellRequestDto.productId());
        try {
            taskQueue.put(sellRequestDto); // Adiciona a tarefa à fila
        } catch (InterruptedException e) {
            logger.error("Erro ao tentar enfileirar tarefa: {}", e.getMessage());
        }
    }

    private void processSellTask(SellRequestDto sellRequestDto) {
        logger.info("Processando tarefa em background para o produto ID: {}", sellRequestDto.productId());

        try {
            UUID transactionId = storeProcess.retrySellProduct(sellRequestDto);
            if (transactionId != null) {
                logger.info("Tarefa concluída com sucesso para o produto ID {}. ID da transação: {}", sellRequestDto.productId(), transactionId);
            }else{
                enqueueSellTask(sellRequestDto);
            }
        } catch (Fail f) {
            logger.error("Erro ao processar a tarefa para o produto ID {}: {}", sellRequestDto.productId(), f.getMessage());
        }
    }

    @Override
    public void run() {
        while (true) {
            try {
                SellRequestDto sellRequestDto = taskQueue.take();
                processSellTask(sellRequestDto);

               sleep(1000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                logger.error("Thread de processamento interrompida: {}", e.getMessage());
            }
        }
    }
}
