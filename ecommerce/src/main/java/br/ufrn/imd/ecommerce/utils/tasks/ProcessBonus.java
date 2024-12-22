package br.ufrn.imd.ecommerce.utils.tasks;

import br.ufrn.imd.ecommerce.dto.FidelityRequestDto;

public interface ProcessBonus {
    void retryProcessBonus();

    void addBonusToProcessLater(FidelityRequestDto fidelity);
}