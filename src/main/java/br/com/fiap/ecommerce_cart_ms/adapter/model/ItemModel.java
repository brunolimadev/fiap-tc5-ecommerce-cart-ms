package br.com.fiap.ecommerce_cart_ms.adapter.model;

import br.com.fiap.ecommerce_cart_ms.domain.exception.EntityException;
import io.swagger.v3.oas.annotations.Hidden;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.time.LocalDateTime;

import static br.com.fiap.ecommerce_cart_ms.utils.MessageEnumUtils.ENTITY_EXCEPTION;

@ToString
@Getter
@Setter
@Builder
public class ItemModel {

  @Hidden
  private Long id;
  private String description;
  private Double price;
  private Integer storeQuantity;
  @Hidden
  private LocalDateTime createDateTime;
  @Hidden
  private LocalDateTime updateDateTime;

  public ItemModel(
          Long id, String description, Double price, Integer storeQuantity,
          LocalDateTime createDateTime, LocalDateTime updateDateTime
  ) {

    validateValues(description, price, storeQuantity);

    this.id = id;
    this.description = description;
    this.price = price;
    this.storeQuantity = storeQuantity;
    this.createDateTime = createDateTime;
    this.updateDateTime = updateDateTime;

  }

  private void validateValues (
          String description, Double price, Integer storeQuantity
  ) throws EntityException {

    if (
            description == null || price == null || storeQuantity == null
    ) {

      throw new EntityException(ENTITY_EXCEPTION.getMessage());

    }

    if (
            description.isEmpty() || price <= 0 || storeQuantity <= 0
    ) {

      throw new EntityException(ENTITY_EXCEPTION.getMessage());

    }

  }


}