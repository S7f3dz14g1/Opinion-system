package com.szwedo.opinionsystem.service;

import com.szwedo.opinionsystem.model.ProductDetails;

public interface ProductDetailsService {
  ProductDetails getProductDetailsById(Long productId);
}