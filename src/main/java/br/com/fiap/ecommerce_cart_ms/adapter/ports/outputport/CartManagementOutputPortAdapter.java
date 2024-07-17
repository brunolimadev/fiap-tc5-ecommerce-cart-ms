package br.com.fiap.ecommerce_cart_ms.adapter.ports.outputport;

import br.com.fiap.ecommerce_cart_ms.adapter.model.ItemModel;
import br.com.fiap.ecommerce_cart_ms.domain.entities.CartEntity;
import br.com.fiap.ecommerce_cart_ms.domain.entities.ItemEntity;
import br.com.fiap.ecommerce_cart_ms.domain.exception.EntityException;
import br.com.fiap.ecommerce_cart_ms.ports.exception.OutputPortException;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.CartManagementOutputPort;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.ItemManagementOutputPort;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.SessionManagementOutputPort;
import com.google.gson.Gson;
import org.json.JSONObject;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

import static br.com.fiap.ecommerce_cart_ms.utils.MessageEnumUtils.*;

public class CartManagementOutputPortAdapter implements CartManagementOutputPort {

  private final ItemManagementOutputPort itemManagementOutputPort;
  private final SessionManagementOutputPort sessionManagementOutputPort;

  public CartManagementOutputPortAdapter(
          ItemManagementOutputPort itemManagementOutputPort,
          SessionManagementOutputPort sessionManagementOutputPort) {

    this.itemManagementOutputPort = itemManagementOutputPort;
    this.sessionManagementOutputPort = sessionManagementOutputPort;

  }

  @Override
  public CartEntity addCartItem(ItemEntity itemEntity, String sessionId) throws OutputPortException {

    try {

      var sessionData = sessionManagementOutputPort.getSession(sessionId);
      validateSessionValues(sessionData);

      var cart = createNewCartOrGetSessionCart(sessionData);

      var itemModel = itemManagementOutputPort.getItem(itemEntity.getId(), sessionId);
      validateStoreQuantityAndCardItemQuantity(itemModel, itemEntity);
      updateItemAndAddItemsList(itemModel, itemEntity, cart, sessionId);

      var items = cart.getItems();
      var response = CartEntity.builder().totalOrder(calculateTotalOrder(items)).items(items).build();
      sessionManagementOutputPort.updateSession(sessionId, response);

      return response;

    } catch (EntityException | OutputPortException exception) {

      throw exception;

    } catch (Exception exception) {

      throw new OutputPortException(ITEM_MANAGEMENT_CREATE_ITEMS_OUTPUT_PORT_EXCEPTION.getMessage());

    }

  }

  @Override
  public CartEntity removeCartItem(Long id, String sessionId) throws OutputPortException {

    try {

      var sessionData = sessionManagementOutputPort.getSession(sessionId);
      validateSessionValues(sessionData);

      var cart = createNewCartOrGetSessionCart(sessionData);

      var itemModel = itemManagementOutputPort.getItem(id, sessionId);
      updateItemAndRemoveItemsList(id, itemModel, cart, sessionId);

      var items = cart.getItems();
      var response = CartEntity.builder().totalOrder(calculateTotalOrder(items)).items(items).build();
      sessionManagementOutputPort.updateSession(sessionId, response);

      return response;

    } catch (Exception exception) {

      throw new OutputPortException(ITEM_MANAGEMENT_REMOVE_ITEM_OUTPUT_PORT_EXCEPTION.getMessage());

    }

  }

  private CartEntity createNewCartOrGetSessionCart(Object sessionData) {

    var sessionJsonString = new Gson().toJson(sessionData);
    var sessionJsonObject = new JSONObject(sessionJsonString)
            .getJSONObject("sessionData");

    if (!sessionJsonObject.has("shopping_cart")) {

      return CartEntity.builder().items(Collections.emptyList()).build();

    }

    return getSessionCart(sessionData);

  }

  private CartEntity getSessionCart(Object sessionData) {

    var sessionJsonString = new Gson().toJson(sessionData);
    var sessionJsonObject = new JSONObject(sessionJsonString)
            .getJSONObject("sessionData")
            .getJSONObject("shopping_cart");

    return new Gson().fromJson(sessionJsonObject.toString(), CartEntity.class);

  }

  private void validateSessionValues(Object object) {

    if (object == null) {

      throw new OutputPortException("Usuario sem dados de sessão");

    }

  }

  private void validateStoreQuantityAndCardItemQuantity(
          ItemModel itemModel,
          ItemEntity itemEntity) throws OutputPortException {

    assert itemModel != null;

    if (itemModel.getStoreQuantity() < itemEntity.getQuantity()) {

      throw new OutputPortException("A quantidade do item informada excede a disponibilidade do item em estoque");

    }

  }

  private void updateItemAndAddItemsList(
          ItemModel itemModel, ItemEntity itemEntity,
          CartEntity sessionCart, String sessionId) {

      itemModel.setStoreQuantity(itemModel.getStoreQuantity() - itemEntity.getQuantity());

      itemManagementOutputPort.updateItem(itemEntity.getId(), itemModel, sessionId);

      var cardItem = ItemEntity.builder()
              .id(itemModel.getId())
              .price(itemModel.getPrice())
              .description(itemModel.getDescription())
              .quantity(itemEntity.getQuantity())
              .build();

      sessionCart.getItems().add(cardItem);

  }

  private void updateItemAndRemoveItemsList(
          Long id, ItemModel itemModel,
          CartEntity sessionCart, String sessionId) {

    assert itemModel != null;

    var removeItem = sessionCart.getItems().stream()
            .filter(itemEntity -> Objects.equals(itemEntity.getId(), id))
            .findAny()
            .orElse(null);

    sessionCart.getItems().remove(removeItem);

    assert removeItem != null;

    itemModel.setStoreQuantity(itemModel.getStoreQuantity() + removeItem.getQuantity());

    itemManagementOutputPort.updateItem(id, itemModel,sessionId);

  }

  private Double calculateTotalOrder(List<ItemEntity> items) {

    var totalOrder = 0.0;

    for (ItemEntity item: items) {

      totalOrder += item.getPrice() * item.getQuantity();

    }

    return totalOrder;

  }

}