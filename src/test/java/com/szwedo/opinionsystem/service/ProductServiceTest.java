package com.szwedo.opinionsystem.service;

import com.szwedo.opinionsystem.dao.ProductRepository;
import com.szwedo.opinionsystem.entity.Product;
import com.szwedo.opinionsystem.exception.DatasourceFailureException;
import com.szwedo.opinionsystem.exception.ProductNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductServiceTest {

  @InjectMocks
  private ProductServiceImpl service;
  @Mock
  private ProductRepository repository;

  @Test
  void should_return_product_when_product_exists() {
    //given
    long productId = 1;
    Optional<Product> product = Optional.of(Product.builder()
        .id(productId)
        .name("productName")
        .description("description")
        .price(2.3)
        .build()
    );
    //when
    when(repository.findById(productId)).thenReturn(product);

    //then
    assertEquals(service.getProductById(productId), product.get());
  }

  @Test
  void should_throw_ProductNotFoundException_when_product_does_not_exist() {
    //given
    long productId = 1;
    //when
    when(repository.findById(productId)).thenReturn(Optional.empty());

    //then
    assertThrows(ProductNotFoundException.class, () ->
        service.getProductById(productId));
  }

  @Test
  void should_throw_exception_when_the_server_is_not_responding() {
    //given
    long productId = 1;
    //when
    when(repository.findById(productId)).thenThrow(DatasourceFailureException.class);
    when(repository.findAll()).thenThrow(DatasourceFailureException.class);
    //then
    assertThrows(DatasourceFailureException.class, () -> service.getProductById(1L));
    assertThrows(DatasourceFailureException.class, () -> service.getAllProduct());
  }

  @Test
  void should_return_list_of_products() {
    //given
    List<Product> productList = List.of(
        Product.builder()
            .id(1L)
            .description("description")
            .name("name")
            .price(1.2)
            .build()
    );
    //when
    when(repository.findAll()).thenReturn(productList);
    //then
    assertEquals(productList, service.getAllProduct());
  }
}