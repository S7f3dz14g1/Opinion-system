package com.szwedo.opinionsystem.api;

import com.szwedo.opinionsystem.entity.Product;
import com.szwedo.opinionsystem.service.ProductService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("api/product")
public class ProductController {

  private final ProductService service;

  @GetMapping
  public List<Product> getAllProducts() {
    return service.getAllProduct();
  }

  @GetMapping(value = "{id}")
  public Product getProductById(@PathVariable("id") Long id) {
    return service.getProductById(id);
  }
}