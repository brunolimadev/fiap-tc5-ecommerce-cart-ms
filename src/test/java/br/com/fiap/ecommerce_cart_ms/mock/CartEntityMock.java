package br.com.fiap.ecommerce_cart_ms.mock;

import br.com.fiap.ecommerce_cart_ms.domain.entities.CartEntity;

public class CartEntityMock {

  public static CartEntity get() {

    return CartEntity
            .builder()
            .id(1L)
            .totalOrder(0.0)
            .items(ItemEntityMock.getList())
            .build();

  }

}