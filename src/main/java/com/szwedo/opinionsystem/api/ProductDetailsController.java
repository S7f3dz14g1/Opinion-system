package com.szwedo.opinionsystem.api;

import com.szwedo.opinionsystem.model.ProductDetails;
import com.szwedo.opinionsystem.service.ProductDetailsService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController("api/product/details")
@AllArgsConstructor
public class ProductDetailsController {

  private final ProductDetailsService service;

  @GetMapping(value = "/{id}")
  public ProductDetails getProductDetails(@PathVariable("id") Long productId) {
    return service.getProductDetailsById(productId);
  }
}