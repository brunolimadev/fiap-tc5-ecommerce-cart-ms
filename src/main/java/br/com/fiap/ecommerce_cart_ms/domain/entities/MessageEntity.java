package br.com.fiap.ecommerce_cart_ms.domain.entities;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class MessageEntity {

  private String title;
  private String message;

}