package br.com.fiap.ecommerce_cart_ms.adapter.ports.outputport;

import br.com.fiap.ecommerce_cart_ms.adapter.model.ItemModel;
import br.com.fiap.ecommerce_cart_ms.domain.entities.CartEntity;
import br.com.fiap.ecommerce_cart_ms.domain.entities.ItemEntity;
import br.com.fiap.ecommerce_cart_ms.domain.exception.EntityException;
import br.com.fiap.ecommerce_cart_ms.ports.exception.OutputPortException;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.CartManagementOutputPort;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static br.com.fiap.ecommerce_cart_ms.utils.MessageEnumUtils.*;

public class CartManagementOutputPortAdapter implements CartManagementOutputPort {

  private static final List<ItemEntity> items = new ArrayList<>();

  @Override
  public CartEntity addCartItem(ItemEntity itemEntity) throws OutputPortException {

    try {

      WebClient client = WebClient.create("http://localhost:8082");

      Mono<ItemModel> response = client.get()
              .uri("/ecommerce-management/api/v1/items/{id}", itemEntity.getId())
              .retrieve()
              .bodyToMono(ItemModel.class);

      var itemModel = response.block();

      assert itemModel != null;

      if (itemModel.getStoreQuantity() < itemEntity.getQuantity()) {

          throw new OutputPortException("A quantidade do item informada excede a disponibilidade do item em estoque");

      }

      itemModel.setStoreQuantity(itemModel.getStoreQuantity() - itemEntity.getQuantity());

      Mono<ItemModel> response1 = client.put()
              .uri("/ecommerce-management/api/v1/items/{id}", itemEntity.getId())
              .body(BodyInserters.fromValue(itemModel))
              .retrieve()
              .bodyToMono(ItemModel.class);

      response1.block();

      var cardItem = ItemEntity.builder()
              .id(itemModel.getId())
              .price(itemModel.getPrice())
              .description(itemModel.getDescription())
              .quantity(itemEntity.getQuantity())
              .build();

      var totalOrder = 0.0;
      items.add(cardItem);

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

      WebClient client = WebClient.create("http://localhost:8082");

      Mono<ItemModel> response = client.get()
              .uri("/ecommerce-management/api/v1/items/{id}", id)
              .retrieve()
              .bodyToMono(ItemModel.class);

      var itemModel = response.block();

      assert itemModel != null;

      var totalOrder = 0.0;
      var removeItem = items.stream()
              .filter(itemEntity -> Objects.equals(itemEntity.getId(), id))
              .findAny()
              .orElse(null);

      items.remove(removeItem);

      for (ItemEntity item: items) {

        totalOrder += item.getPrice() * item.getQuantity();

      }

      assert removeItem != null;
      itemModel.setStoreQuantity(itemModel.getStoreQuantity() + removeItem.getQuantity());

      Mono<ItemModel> response1 = client.put()
              .uri("/ecommerce-management/api/v1/items/{id}", id)
              .body(BodyInserters.fromValue(itemModel))
              .retrieve()
              .bodyToMono(ItemModel.class);

      response1.block();

      return CartEntity.builder().totalOrder(totalOrder).items(items).build();

    } catch (Exception exception) {

      throw new OutputPortException(ITEM_MANAGEMENT_REMOVE_ITEM_OUTPUT_PORT_EXCEPTION.getMessage());

    }

  }

}