package br.com.fiap.ecommerce_cart_ms.ports.outputport;


import br.com.fiap.ecommerce_cart_ms.domain.entities.CartEntity;
import br.com.fiap.ecommerce_cart_ms.domain.entities.ItemEntity;
import br.com.fiap.ecommerce_cart_ms.ports.exception.OutputPortException;

public interface CartManagementOutputPort {

  CartEntity addCartItem(ItemEntity itemEntity, String sessionId) throws OutputPortException;

  CartEntity removeCartItem(Long id, String sessionId) throws OutputPortException;
  
}