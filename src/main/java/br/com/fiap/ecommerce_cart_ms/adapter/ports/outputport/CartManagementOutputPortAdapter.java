package br.com.fiap.ecommerce_cart_ms.adapter.ports.outputport;

import br.com.fiap.ecommerce_cart_ms.domain.entities.CartEntity;
import br.com.fiap.ecommerce_cart_ms.domain.entities.ItemEntity;
import br.com.fiap.ecommerce_cart_ms.domain.exception.EntityException;
import br.com.fiap.ecommerce_cart_ms.ports.exception.OutputPortException;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.CartManagementOutputPort;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.fiap.ecommerce_cart_ms.utils.MessageEnumUtils.*;

public class CartManagementOutputPortAdapter implements CartManagementOutputPort {

  private static final List<ItemEntity> items = new ArrayList<>();

  @Override
  public CartEntity addCartItem(ItemEntity itemEntity) throws OutputPortException {

    try {

      var totalOrder = 0.0;
      items.add(itemEntity);

      for (ItemEntity item: items) {
        totalOrder += item.getPrice() * item.getQuantity();
      }

      return CartEntity.builder().totalOrder(totalOrder).items(items).build();

    } catch (EntityException entityException) {

      throw entityException;

    } catch (Exception exception) {

      throw new OutputPortException(ITEM_MANAGEMENT_CREATE_ITEMS_OUTPUT_PORT_EXCEPTION.getMessage());

    }

  }

  @Override
  public CartEntity removeCartItem(Long id) throws OutputPortException {

    try {

      var totalOrder = 0.0;
      var removeItem = items.stream()
              .filter(itemEntity -> Objects.equals(itemEntity.getId(), id))
              .findAny()
              .orElse(null);

      items.remove(removeItem);

      for (ItemEntity item: items) {
        totalOrder += item.getPrice() * item.getQuantity();
      }

      return CartEntity.builder().totalOrder(totalOrder).items(items).build();

    } catch (Exception exception) {

      throw new OutputPortException(ITEM_MANAGEMENT_REMOVE_ITEM_OUTPUT_PORT_EXCEPTION.getMessage());

    }

  }

}