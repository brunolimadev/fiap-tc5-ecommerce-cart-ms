package br.com.fiap.ecommerce_cart_ms.controller;

import br.com.fiap.ecommerce_cart_ms.domain.entities.CartEntity;
import br.com.fiap.ecommerce_cart_ms.domain.entities.ItemEntity;
import br.com.fiap.ecommerce_cart_ms.mock.CartEntityMock;
import br.com.fiap.ecommerce_cart_ms.mock.ItemEntityMock;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.CartManagementOutputPort;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

@WebMvcTest(controllers = CartController.class)
@ExtendWith(MockitoExtension.class)
class CartControllerTest {

  @Autowired
  private MockMvc mockMvc;

  @Autowired
  private ObjectMapper objectMapper;

  @MockBean
  private CartManagementOutputPort cartManagementOutputPort;

  private CartEntity cartEntity;

  private ItemEntity itemEntity;

  @BeforeEach
  public void init () {

    cartEntity = CartEntityMock.get();
    itemEntity = ItemEntityMock.get();

  }

  @Test
  void shouldCreateItemWithSuccess() throws Exception {

    //Arrange
    given(cartManagementOutputPort.addCartItem(ItemEntityMock.get(), "")).willAnswer(invocation -> invocation.getArgument(0));

    //Act
    var response = mockMvc.perform(post("http://localhost:8080/cart")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(itemEntity)));

    //Assert
    response
            .andExpect(MockMvcResultMatchers.status().isCreated());

  }

  @Test
  void shouldRemoveItemWithSuccess() throws Exception {

    //Arrange
    var cartEntityId = itemEntity.getId();
    when(cartManagementOutputPort.removeCartItem(cartEntityId, "")).thenReturn(cartEntity);

    //Act
    var response = mockMvc.perform(delete("http://localhost:8080/cart/1")
            .contentType(MediaType.APPLICATION_JSON)
            .content(objectMapper.writeValueAsString(itemEntity)));

    //Assert
    response
            .andExpect(MockMvcResultMatchers.status().isOk());

  }

}