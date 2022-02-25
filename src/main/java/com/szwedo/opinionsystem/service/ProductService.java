package com.szwedo.opinionsystem.service;

import com.szwedo.opinionsystem.entity.Product;

import java.util.List;

public interface ProductService {
  Product getProductById(Long id);

  List<Product> getAllProduct();
}