package br.com.fiap.ecommerce_cart_ms.controller;

import br.com.fiap.ecommerce_cart_ms.domain.entities.CartEntity;
import br.com.fiap.ecommerce_cart_ms.domain.entities.ItemEntity;
import br.com.fiap.ecommerce_cart_ms.ports.exception.OutputPortException;
import br.com.fiap.ecommerce_cart_ms.ports.outputport.CartManagementOutputPort;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("cart")
@Tag(name = "Cart Controller", description = "Customer can manage cart through API resources")
public class CartController {

  private final CartManagementOutputPort cartManagementOutputPort;

  public CartController(
          CartManagementOutputPort cartManagementOutputPort
  ) {
    
    this.cartManagementOutputPort = cartManagementOutputPort;

  }

  @Operation(summary = "Add cart item")
  @ApiResponse(
          responseCode = "201",
          description = "Returns updated cart details"
  )
  @PostMapping
  public ResponseEntity<CartEntity> addCartItem(
          @RequestBody ItemEntity itemEntity,
          @RequestHeader("session-id") String sessionId
  ) throws OutputPortException {

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(cartManagementOutputPort.addCartItem(itemEntity, sessionId));

  }

  @Operation(summary = "Remove cart item by id")
  @ApiResponse(responseCode = "200", description = "Returns updated cart details")
  @DeleteMapping(value = "{item_id}")
  public ResponseEntity<CartEntity> removeCartItem(
          @PathVariable("item_id") Long id,
          @RequestHeader("session-id") String sessionId
  ) throws OutputPortException {

    return  ResponseEntity
            .status(HttpStatus.OK)
            .body(cartManagementOutputPort.removeCartItem(id, sessionId));

  }

}