package br.com.fiap.ecommerce_cart_ms.domain.entities;

import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@Builder
public class CartEntity {

  @Hidden
  private String id;
  private Double totalOrder;
  private List<ItemEntity> items;

}