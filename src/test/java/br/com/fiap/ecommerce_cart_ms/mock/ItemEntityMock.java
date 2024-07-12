package br.com.fiap.ecommerce_cart_ms.mock;


import br.com.fiap.ecommerce_cart_ms.domain.entities.ItemEntity;

import java.util.Collections;
import java.util.List;

public class ItemEntityMock {

  public static ItemEntity get() {

    return ItemEntity
            .builder()
            .id(1L)
            .description("Camiseta da Adidas GG")
            .price(20.90)
            .quantity(2)
            .build();

  }

  public static List<ItemEntity> getList() {

    return Collections.singletonList(get());

  }

}