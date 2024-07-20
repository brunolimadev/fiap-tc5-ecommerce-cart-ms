package br.com.fiap.ecommerce_cart_ms.adapter.config;

import br.com.fiap.ecommerce_cart_ms.adapter.ports.outputport.CartManagementOutputPortAdapter;
import br.com.fiap.ecommerce_cart_ms.adapter.ports.outputport.ItemManagementOutputPortAdapter;
import br.com.fiap.ecommerce_cart_ms.adapter.ports.outputport.SessionManagementOutputPortAdapter;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.CartManagementOutputPort;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.ItemManagementOutputPort;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.SessionManagementOutputPort;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortsConfig {

  @Value("${application.session.host-url}")
  private String sessionServiceHost;

  @Value("${application.item.host-url}")
  private String itemServiceHost;

  @Bean
  public ItemManagementOutputPort itemManagementOutputPort() {

    return new ItemManagementOutputPortAdapter(itemServiceHost);

  }

  @Bean
  public SessionManagementOutputPort sessionManagementOutputPort() {

    return new SessionManagementOutputPortAdapter(sessionServiceHost);

  }

  @Bean
  public CartManagementOutputPort cartManagementOutputPort(
      ItemManagementOutputPort itemManagementOutputPort,
      SessionManagementOutputPort sessionManagementOutputPort) {

    return new CartManagementOutputPortAdapter(
        itemManagementOutputPort,
        sessionManagementOutputPort);

  }

}
