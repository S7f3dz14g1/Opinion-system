package com.szwedo.opinionsystem.api;

import com.szwedo.opinionsystem.entity.Opinion;
import com.szwedo.opinionsystem.entity.Product;
import com.szwedo.opinionsystem.model.ProductDetails;
import com.szwedo.opinionsystem.service.ProductDetailsService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith({MockitoExtension.class})
class ProductDetailsControllerTest {

  @InjectMocks
  private ProductDetailsController controller;
  @Mock
  private ProductDetailsService service;

  @Test
  void should_return_product_details(){
    //given
    List<Opinion> opinionList = List.of(
        Opinion.builder()
            .productId(1L)
            .opinionId(1L)
            .createdDate(OffsetDateTime.now())
            .author("Author")
            .content("Good product")
            .stars(5)
            .build()
    );
    Optional<Product> product = Optional.of(Product.builder()
        .id(1L)
        .name("Product")
        .description("description")
        .price(1.2)
        .build());
    ProductDetails expectedProductDetails = ProductDetails.builder()
        .product(product.get())
        .average(5)
        .opinions(opinionList)
        .build();
    //when
    when(service.getProductDetailsById(1L)).thenReturn(expectedProductDetails);
    //then
    assertEquals(expectedProductDetails,controller.getProductDetails(1L));
  }
}