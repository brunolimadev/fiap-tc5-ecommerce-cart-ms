package br.com.fiap.ecommerce_cart_ms.ports.outputport;

public interface SessionManagementOutputPort {

  Object getSession(String sessionId);

  Object updateSession(String sessionId, Object object);

}