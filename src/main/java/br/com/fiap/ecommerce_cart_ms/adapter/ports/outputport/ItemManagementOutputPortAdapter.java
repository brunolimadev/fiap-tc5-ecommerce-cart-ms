package br.com.fiap.ecommerce_cart_ms.adapter.ports.outputport;

import br.com.fiap.ecommerce_cart_ms.adapter.model.ItemModel;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.ItemManagementOutputPort;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

public class ItemManagementOutputPortAdapter implements ItemManagementOutputPort {

  private final WebClient client = WebClient.create("http://localhost:8082");

  @Override
  public ItemModel getItem(Long id) {

    Mono<ItemModel> response = client.get()
            .uri("/ecommerce-management/api/v1/items/{id}", id)
            .retrieve()
            .bodyToMono(ItemModel.class);

    return response.block();

  }

  @Override
  public void updateItem(Long id, ItemModel itemModel) {

    Mono<ItemModel> response = client.put()
            .uri("/ecommerce-management/api/v1/items/{id}", id)
            .body(BodyInserters.fromValue(itemModel))
            .retrieve()
            .bodyToMono(ItemModel.class);

    response.block();

  }

}