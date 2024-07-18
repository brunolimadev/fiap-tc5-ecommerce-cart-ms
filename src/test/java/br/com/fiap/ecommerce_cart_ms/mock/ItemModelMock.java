package br.com.fiap.ecommerce_cart_ms.mock;


import br.com.fiap.ecommerce_cart_ms.adapter.model.ItemModel;

public class ItemModelMock {

  public static ItemModel get() {

    return ItemModel
            .builder()
            .id(1L)
            .description("Camiseta da Adidas GG")
            .price(20.90)
            .storeQuantity(2)
            .build();

  }

}