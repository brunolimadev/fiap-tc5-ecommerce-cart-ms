package br.com.fiap.ecommerce_cart_ms.domain.entities;

import br.com.fiap.ecommerce_cart_ms.domain.exception.EntityException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.*;

import static br.com.fiap.ecommerce_cart_ms.utils.MessageEnumUtils.ENTITY_EXCEPTION;

@Getter
@Builder
public class ItemEntity {

  @Hidden
  private Long id;
  private String description;
  private Double price;
  private Integer quantity;

  public ItemEntity(
          Long id, String description, Double price, Integer quantity
  ) {

    validateValues(description, price, quantity);

    this.id = id;
    this.description = description;
    this.price = price;
    this.quantity = quantity;

  }

  private void validateValues (
          String description, Double price, Integer quantity
  ) throws EntityException {

    if (
            description == null || price == null || quantity == null
    ) {

      throw new EntityException(ENTITY_EXCEPTION.getMessage());

    }

    if (
            description.isEmpty() || price <= 0 || quantity <= 0
    ) {

      throw new EntityException(ENTITY_EXCEPTION.getMessage());

    }

  }

}