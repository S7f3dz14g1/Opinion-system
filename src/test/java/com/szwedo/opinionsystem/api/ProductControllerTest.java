package com.szwedo.opinionsystem.api;

import com.szwedo.opinionsystem.entity.Product;
import com.szwedo.opinionsystem.service.ProductService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class ProductControllerTest {

  @InjectMocks
  private ProductController controller;
  @Mock
  private ProductService service;

  @Test
  void should_return_list_of_product() {
    //given
    List<Product> products=List.of(
        Product.builder()
            .id(1L)
            .name("someName")
            .description("someDescription")
            .price(1.2)
            .build());
    //when
    when(service.getAllProduct()).thenReturn(products);
    //then
    assertEquals(controller.getAllProducts(),products);
  }

  @Test
  void should_return_product_when_product_exists() {
    //given
    Long productId=1L;
    Product product=Product.builder()
        .id(productId)
        .name("someName")
        .description("someDescription")
        .price(1.3)
        .build();
    //when
    when(service.getProductById(productId)).thenReturn(product);
    //then
    assertEquals(controller.getProductById(productId),product);
  }
}