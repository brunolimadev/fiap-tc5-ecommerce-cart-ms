package br.com.fiap.ecommerce_cart_ms.adapter.config;

import br.com.fiap.ecommerce_cart_ms.adapter.ports.outputport.CartManagementOutputPortAdapter;
import br.com.fiap.ecommerce_cart_ms.adapter.ports.outputport.ItemManagementOutputPortAdapter;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.CartManagementOutputPort;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.ItemManagementOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortsConfig {

  @Bean
  public ItemManagementOutputPort itemManagementOutputPort() {

    return new ItemManagementOutputPortAdapter();

  }

  @Bean
  public CartManagementOutputPort cartManagementOutputPort(ItemManagementOutputPort itemManagementOutputPort) {

    return new CartManagementOutputPortAdapter(itemManagementOutputPort);

  }

}
