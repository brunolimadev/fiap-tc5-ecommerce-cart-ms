package br.com.fiap.ecommerce_cart_ms.domain.entities;

import br.com.fiap.ecommerce_cart_ms.domain.exception.EntityException;
import org.junit.jupiter.api.Test;

import static br.com.fiap.ecommerce_cart_ms.utils.MessageEnumUtils.ENTITY_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThatNoException;
import static org.assertj.core.api.Assertions.assertThatThrownBy;


class ItemEntityTest {

  @Test
  void shouldCreateItemEntity() {

    assertThatNoException().isThrownBy(() -> ItemEntity
            .builder()
            .description("Camisa polo")
            .price(200.0)
            .quantity(1)
            .build()
    );

  }

  @Test
  void shouldThrowEntityExceptionTryingCreateItemEntityWithNullDescription() {

    assertThatThrownBy(() -> ItemEntity
            .builder()
            .price(200.0)
            .quantity(1)
            .build()
    )
            .isInstanceOf(EntityException.class)
            .hasMessage(ENTITY_EXCEPTION.getMessage());

  }

  @Test
  void shouldThrowEntityExceptionTryingCreateItemEntityWithPriceZero() {

    assertThatThrownBy(() -> ItemEntity
            .builder()
            .description("Camisa polo")
            .price(0.0)
            .quantity(1)
            .build()
    )
            .isInstanceOf(EntityException.class)
            .hasMessage(ENTITY_EXCEPTION.getMessage());

  }

}