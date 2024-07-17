package br.com.fiap.ecommerce_cart_ms.ports.outputport;

import br.com.fiap.ecommerce_cart_ms.adapter.model.ItemModel;

public interface ItemManagementOutputPort {

  ItemModel getItem(Long id, String sessionId);

  void updateItem(Long id, ItemModel itemModel, String sessionId);

}