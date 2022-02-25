package com.szwedo.opinionsystem.service;

import com.szwedo.opinionsystem.dao.OpinionRepository;
import com.szwedo.opinionsystem.dao.ProductRepository;
import com.szwedo.opinionsystem.entity.Opinion;
import com.szwedo.opinionsystem.entity.Product;
import com.szwedo.opinionsystem.exception.ProductNotFoundException;
import com.szwedo.opinionsystem.model.ProductDetails;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
class ProductDetailsServiceImpl implements ProductDetailsService {

  private final OpinionRepository opinionRepository;
  private final ProductRepository productRepository;

  @Override
  public ProductDetails getProductDetailsById(Long productId) {
    Optional<Product> optionalProduct = productRepository.findById(productId);
    if (optionalProduct.isPresent()) {
      log.info("The product with id {} was returned", productId);
      List<Opinion> opinions = opinionRepository.getAllOpinionByProductId(productId);
      return ProductDetails
          .builder()
          .product(optionalProduct.get())
          .average(opinions.stream().mapToInt(Opinion::getStars).average().orElse(0))
          .opinions(opinions)
          .build();
    } else {
      log.warn("The product with id {} does not found", productId);
      throw new ProductNotFoundException(productId);
    }
  }
}