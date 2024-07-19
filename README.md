# ⭐ FIAP - Pós Tech (Fase 5) - **API de gerenciamento de carrinho de compras de ecommerce**

---

## 💥 Descrição
- O projeto "Gerenciamento de carrinho de compras de ecommerce" foi criado como critério de avaliação na 5ª fase da pós tech FIAP

## 🛠️ Funcionalidades

- **Adiciona um novo item no carrinho de compras**
  - Dado uma requisição com os valores de um novo item o adiciona no carrinho, atualiza o estoque de itens, salva na sessão do cliente e retorna os detalhes do carrinho atualizado

- **Remove um item do carrinho de compras por id**
    - Dado um código de item o remove do carrinho, atualiza o estoque de itens, atualiza o carrinho na sessão, e retorna os detalhes do carrinho atualizado

## 🚀 Sobre a Aplicação
- **Desenvolvida utilizando boas práticas de “Clean code”, com arquitetura hexagonal aplicada**
- **Cobertura de testes unitários**
- **Deve ser acessada por meio do MS**
  - **https://github.com/brunolimadev/fiap-tc5-ecommerce-orchestrator-ms**
- **Realiza integração com os MS´s**
  - **https://github.com/brunolimadev/fiap-tc5-ecommerce-session-ms**
  - **https://github.com/brunolimadev/fiap-tc5-ecommerce-item-ms**

## 🛠️ Repositório
- #### https://github.com/brunolimadev/fiap-tc5-ecommerce-cart-ms

## 🚀 Tecnologias Utilizadas
- **Spring Boot:** versão 3.3.1
- **Spring Webflux:** versão 3.1.2
- **Java:** versão 17
- **Springdoc-openapi-Swagger:** versão 2.5.0
- **Lombok:**  versão 1.18.32
- **JUnit:** versão: 5.10.2

## 🛠️ Ferramentas Utilizadas
- [GitHub](https://github.com/)
- [IntelliJ IDEA](https://www.jetbrains.com/idea/)
- [Postman](https://www.postman.com/)
- [Draw.io](https://app.diagrams.net/)

## 📋 Swagger
- http://localhost:8083/ecommerce-management/api/v1/swagger-ui/index.html#/

## 😎 Collection
- [fiap-ecommerce-cart.postman_collection.zip](https://github.com/brunolimadev/fiap-tc5-ecommerce-cart-ms/blob/develop/api-test-files/fiap-ecommerce-cart.postman_collection.zip?raw=true)
  - **Obs.:** para utilizar a coleção primeiro é preciso descompactar o arquivo e importá-lo no Postman

## ⭐ Squad
- **Grupo 57**
  - Bruno Rafael de Lima da Rocha
  - Eric Leonardo Santos Rangel
  - Wiliam Nascimento da Silva
  - Lucas Aparecido da Silva Mantovani