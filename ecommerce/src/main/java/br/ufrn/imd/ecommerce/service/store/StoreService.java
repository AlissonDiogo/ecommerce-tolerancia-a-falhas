package br.ufrn.imd.ecommerce.service.store;

import br.ufrn.imd.ecommerce.dto.SellRequestDto;
import br.ufrn.imd.ecommerce.model.Product;
import br.ufrn.imd.ecommerce.utils.fails.Fail;

import java.util.UUID;

public interface StoreService {

    Product checkProductById(UUID productId) throws Fail;

    UUID sellProduct(SellRequestDto requestDto) throws Fail;

}
