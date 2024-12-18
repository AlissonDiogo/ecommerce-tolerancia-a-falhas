package br.ufrn.imd.ecommerce.service.store;

import br.ufrn.imd.ecommerce.dto.ProductResponseDto;

public interface StoreService {

    ProductResponseDto checkProductById(String productId);

}
