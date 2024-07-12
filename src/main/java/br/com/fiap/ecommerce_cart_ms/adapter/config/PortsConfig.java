package br.com.fiap.ecommerce_cart_ms.adapter.config;

import br.com.fiap.ecommerce_cart_ms.adapter.ports.outputport.CartManagementOutputPortAdapter;
//import br.com.fiap.ecommerce_cart_ms.adapter.repositories.CartRepository;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.CartManagementOutputPort;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PortsConfig {

  @Bean
  public CartManagementOutputPort itemManagementOutputPort() {

    return new CartManagementOutputPortAdapter();

  }

}
