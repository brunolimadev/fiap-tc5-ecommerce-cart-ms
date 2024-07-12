package br.com.fiap.ecommerce_cart_ms.utils;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public enum MessageEnumUtils {

  ITEM_MANAGEMENT_CREATE_ITEMS_OUTPUT_PORT_EXCEPTION("Ocorreu um erro ao tentar criar o item"),
  ITEM_MANAGEMENT_GET_ITEMS_OUTPUT_PORT_EXCEPTION("Ocorreu um erro ao tentar recuperar os items"),
  ITEM_MANAGEMENT_GET_ITEM_OUTPUT_PORT_EXCEPTION("Ocorreu um erro ao tentar recuperar o item"),
  ITEM_MANAGEMENT_REMOVE_ITEM_OUTPUT_PORT_EXCEPTION("Ocorreu um erro ao tentar remover o item"),
  ITEM_MANAGEMENT_UPDATE_ITEM_OUTPUT_PORT_EXCEPTION("Ocorreu um erro ao tentar atualizar o item"),
  ENTITY_EXCEPTION("Por favor preencha todos os campos"),
  MESSAGE_EMPTY_BODY_SPRING_EXCEPTION("Por favor insira valores validos");

  public static final String TIME_DOMAIN_EXCEPTION = "Erro de negocio";
  public static final String TITLE_PORTS_EXCEPTION =  "Erro de comunicacao";
  public static final String TITLE_DEFAULT_EXCEPTION =  "Erro";

  private String message;

}