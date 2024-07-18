package br.com.fiap.ecommerce_cart_ms.adapter.ports.outputport;

import br.com.fiap.ecommerce_cart_ms.domain.entities.CartEntity;
import br.com.fiap.ecommerce_cart_ms.mock.ItemEntityMock;
import br.com.fiap.ecommerce_cart_ms.mock.ItemModelMock;
import br.com.fiap.ecommerce_cart_ms.mock.SessionModelMock;
import br.com.fiap.ecommerce_cart_ms.ports.exception.OutputPortException;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.CartManagementOutputPort;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.ItemManagementOutputPort;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.SessionManagementOutputPort;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static br.com.fiap.ecommerce_cart_ms.utils.MessageEnumUtils.ITEM_MANAGEMENT_CREATE_ITEMS_OUTPUT_PORT_EXCEPTION;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

class CartManagementOutputPortAdapterTest {

  @Mock
  private ItemManagementOutputPort itemManagementOutputPort;

  @Mock
  private SessionManagementOutputPort sessionManagementOutputPort;

  private CartManagementOutputPort cartManagementOutputPort;
  private AutoCloseable openMocks;

  @BeforeEach
  void setup() {

    openMocks = MockitoAnnotations.openMocks(this);
    cartManagementOutputPort = new CartManagementOutputPortAdapter(
            itemManagementOutputPort,
            sessionManagementOutputPort);

  }

  @AfterEach
  void tearDown() throws Exception {

    openMocks.close();

  }

  @Test
  void shouldAddCartItemSuccessWithCurrentSession() {

    //Arrange
    var sessionObject = SessionModelMock.getCurrent();
    var itemModel = ItemModelMock.get();

    when(sessionManagementOutputPort.getSession(anyString())).thenReturn(sessionObject);

    when(itemManagementOutputPort.getItem(anyLong(), anyString())).thenReturn(itemModel);

    doNothing().when(itemManagementOutputPort).updateItem(anyLong(), any(), anyString());

    //Act

    var response = cartManagementOutputPort.addCartItem(ItemEntityMock.get(), "");

    //Assert
    assertThat(response)
            .isNotNull()
            .isInstanceOf(CartEntity.class);

    assertThat(response.getItems())
            .isNotEmpty();

  }

  @Test
  void shouldAddCartItemSuccessWithNewSession() {

    //Arrange
    var sessionObject = SessionModelMock.getNewSession();
    var itemModel = ItemModelMock.get();

    when(sessionManagementOutputPort.getSession(anyString())).thenReturn(sessionObject);

    when(itemManagementOutputPort.getItem(anyLong(), anyString())).thenReturn(itemModel);

    doNothing().when(itemManagementOutputPort).updateItem(anyLong(), any(), anyString());

    //Act

    var response = cartManagementOutputPort.addCartItem(ItemEntityMock.get(), "");

    //Assert
    assertThat(response)
            .isNotNull()
            .isInstanceOf(CartEntity.class);

    assertThat(response.getItems())
            .isNotEmpty();

  }

  @Test
  void shouldThrowOutputPortExceptionTryingAddCartItemWithItemPurchaseLargerThanStock() {

    //Arrange
    var sessionObject = SessionModelMock.getCurrent();
    var itemModel = ItemModelMock.get();

    itemModel.setStoreQuantity(0);

    when(sessionManagementOutputPort.getSession(anyString())).thenReturn(sessionObject);

    when(itemManagementOutputPort.getItem(anyLong(), anyString())).thenReturn(itemModel);

    doNothing().when(itemManagementOutputPort).updateItem(anyLong(), any(), anyString());

    //Act & Assert
    assertThatThrownBy(() -> cartManagementOutputPort.addCartItem(ItemEntityMock.get(), ""))
            .isInstanceOf(OutputPortException.class)
            .hasMessage("A quantidade do item informada excede a disponibilidade do item em estoque");

  }

  @Test
  void shouldThrowOutputPortExceptionTryingAddCartItemWithCommunicationMsItems() {

    //Arrange
    var sessionObject = SessionModelMock.getCurrent();
    var itemModel = ItemModelMock.get();

    itemModel.setStoreQuantity(0);

    when(sessionManagementOutputPort.getSession(anyString())).thenThrow(RuntimeException.class);

    when(itemManagementOutputPort.getItem(anyLong(), anyString())).thenReturn(itemModel);

    doNothing().when(itemManagementOutputPort).updateItem(anyLong(), any(), anyString());

    //Act & Assert
    assertThatThrownBy(() -> cartManagementOutputPort.addCartItem(ItemEntityMock.get(), ""))
            .isInstanceOf(RuntimeException.class)
            .hasMessage(ITEM_MANAGEMENT_CREATE_ITEMS_OUTPUT_PORT_EXCEPTION.getMessage());

  }

  @Test
  void shouldThrowOutputPortExceptionTryingAddCartItemWithSessionValueNull() {

    //Arrange
    var itemModel = ItemModelMock.get();

    itemModel.setStoreQuantity(0);

    when(sessionManagementOutputPort.getSession(anyString())).thenReturn(null);

    when(itemManagementOutputPort.getItem(anyLong(), anyString())).thenReturn(itemModel);

    doNothing().when(itemManagementOutputPort).updateItem(anyLong(), any(), anyString());

    //Act & Assert
    assertThatThrownBy(() -> cartManagementOutputPort.addCartItem(ItemEntityMock.get(), ""))
            .isInstanceOf(OutputPortException.class)
            .hasMessage("Usuario sem dados de sessão");

  }

  @Test
  void shouldRemoveCartItemWithSuccess() {

    //Arrange
    var sessionObject = SessionModelMock.getCurrent();
    var itemModel = ItemModelMock.get();
    var itemEntity = ItemEntityMock.get();
    var itemEntityId = itemEntity.getId();

    when(sessionManagementOutputPort.getSession(anyString())).thenReturn(sessionObject);

    when(itemManagementOutputPort.getItem(anyLong(), anyString())).thenReturn(itemModel);

    doNothing().when(itemManagementOutputPort).updateItem(anyLong(), any(), anyString());

    //Act
    var response = cartManagementOutputPort.removeCartItem(itemEntityId, "");

    //Assert
    assertThat(response)
            .isNotNull()
            .isInstanceOf(CartEntity.class);

  }

  @Test
  void shouldThrowOutputPortExceptionTryingRemoveCartItemWithCommunicationMsItems() {

    //Arrange
    var sessionObject = SessionModelMock.getCurrent();
    var itemEntity = ItemEntityMock.get();
    var itemEntityId = itemEntity.getId();

    when(sessionManagementOutputPort.getSession(anyString())).thenReturn(sessionObject);

    when(itemManagementOutputPort.getItem(anyLong(), anyString())).thenThrow(OutputPortException.class);

    doNothing().when(itemManagementOutputPort).updateItem(anyLong(), any(), anyString());

    //Act & Assert
    assertThatThrownBy(() -> cartManagementOutputPort.removeCartItem(itemEntityId, ""))
            .isInstanceOf(OutputPortException.class)
            .hasMessage("Ocorreu um erro ao tentar remover o item");

  }

}