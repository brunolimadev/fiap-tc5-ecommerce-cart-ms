package br.com.fiap.ecommerce_cart_ms.domain.entities;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MessageEntityTest {

  @Test
  void shouldCreateMessageEntity() {

    assertThat(MessageEntity
            .builder()
            .title("Título")
            .message("Mensagem")
            .build()
    )
            .isNotNull();

  }

}