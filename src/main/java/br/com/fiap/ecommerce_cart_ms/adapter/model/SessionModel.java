package br.com.fiap.ecommerce_cart_ms.adapter.model;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@ToString
@Getter
@Setter
@Builder
public class SessionModel {

  private String sessionId;
  private String objectKey;
  private Object sessionData;

}