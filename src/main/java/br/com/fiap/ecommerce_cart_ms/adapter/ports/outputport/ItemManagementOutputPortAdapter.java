package br.com.fiap.ecommerce_cart_ms.adapter.ports.outputport;

import br.com.fiap.ecommerce_cart_ms.adapter.model.ItemModel;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.ItemManagementOutputPort;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ItemManagementOutputPortAdapter implements ItemManagementOutputPort {

  private final WebClient client;
  private static final String SESSION_ID_HEADER = "session-id";

  public ItemManagementOutputPortAdapter(@Value("${application.item.host-url}") String sessionServiceHost) {
    this.client = WebClient.create(sessionServiceHost);
  }

  @Override
  public ItemModel getItem(Long id, String sessionId) {

    Mono<ItemModel> response = client.get()
        .uri("/ecommerce-management/api/v1/items/{id}", id)
        .header(SESSION_ID_HEADER, sessionId)
        .retrieve()
        .bodyToMono(ItemModel.class);

    return response.block();

  }

  @Override
  public void updateItem(Long id, ItemModel itemModel, String sessionId) {

    Mono<ItemModel> response = client.put()
        .uri("/ecommerce-management/api/v1/items/{id}", id)
        .header(SESSION_ID_HEADER, sessionId)
        .body(BodyInserters.fromValue(itemModel))
        .retrieve()
        .bodyToMono(ItemModel.class);

    response.block();

  }

}