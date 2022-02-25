package com.szwedo.opinionsystem.service;

import com.szwedo.opinionsystem.dao.OpinionRepository;
import com.szwedo.opinionsystem.dao.ProductRepository;
import com.szwedo.opinionsystem.entity.Opinion;
import com.szwedo.opinionsystem.entity.Product;
import com.szwedo.opinionsystem.exception.DatasourceFailureException;
import com.szwedo.opinionsystem.exception.ProductNotFoundException;
import com.szwedo.opinionsystem.model.ProductDetails;
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

@ExtendWith(MockitoExtension.class)
class ProductDetailsServiceTest {

  @InjectMocks
  private ProductDetailsServiceImpl service;
  @Mock
  private OpinionRepository opinionRepository;
  @Mock
  private ProductRepository productRepository;

  @Test
  void should_return_product_details_when_product_exists() {
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
    when(productRepository.findById(1L)).thenReturn(product);
    when(opinionRepository.getAllOpinionByProductId(1L)).thenReturn(opinionList);
    //then
    assertEquals(service.getProductDetailsById(1L), expectedProductDetails);
  }

  @Test
  void should_throw_exception_when_product_not_found() {
    //given
    long productId = 1;
    //when
    when(productRepository.findById(productId)).thenReturn(Optional.empty());
    //then
    assertThrows(ProductNotFoundException.class, () ->service.getProductDetailsById(productId));}
}