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

import java.util.List;

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

  @Operation(summary = "Create Cart")
  @ApiResponse(
          responseCode = "201",
          description = "Returns a created Cart"
  )
  @PostMapping
  public ResponseEntity<CartEntity> addCartItem(
          @RequestBody ItemEntity itemEntity
  ) throws OutputPortException {

    return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(cartManagementOutputPort.addCartItem(itemEntity));

  }

  @Operation(summary = "Remove a item by id")
  @ApiResponse(responseCode = "200", description = "Returns a removed item")
  @DeleteMapping(value = "{item_id}")
  public ResponseEntity<CartEntity> removeCartItem(@PathVariable("item_id") Long id) throws OutputPortException {

    return  ResponseEntity
            .status(HttpStatus.OK)
            .body(cartManagementOutputPort.removeCartItem(id));

  }

}