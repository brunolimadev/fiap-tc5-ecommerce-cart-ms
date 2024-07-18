package br.com.fiap.ecommerce_cart_ms.mock;

import br.com.fiap.ecommerce_cart_ms.adapter.model.SessionModel;
import br.com.fiap.ecommerce_cart_ms.domain.entities.CartEntity;

import java.util.Map;

public class SessionModelMock {

  public static SessionModel getCurrent() {

    return SessionModel.builder()
            .sessionId("aaaabbaaccc")
            .objectKey("shopping_cart")
            .sessionData(shoppingCart())
            .build();

  }

  public static SessionModel getNewSession() {

    return SessionModel.builder()
            .sessionId("aaaabbaaccc")
            .sessionData(new Object())
            .build();

  }


  private static Map<String, CartEntity> shoppingCart() {

    return Map.of("shopping_cart", CartEntityMock.get());

  }

}