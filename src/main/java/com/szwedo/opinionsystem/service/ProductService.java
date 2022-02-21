package com.szwedo.opinionsystem.service;

import com.szwedo.opinionsystem.dao.ProductRepository;
import com.szwedo.opinionsystem.entity.Product;
import com.szwedo.opinionsystem.exception.DatasourceFailureException;
import com.szwedo.opinionsystem.exception.ProductNotFoundException;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
@Slf4j
public class ProductService {

  private final ProductRepository productRepository;

  public Product getProductById(Long id) {
    return Try.of(() -> productRepository.findById(id))
        .onSuccess(e -> log.info("The product with id {} was returned", id))
        .onFailure(e -> log.warn("The product with id {} does not found", id))
        .getOrElseThrow(DatasourceFailureException::new)
        .orElseThrow(() -> new ProductNotFoundException(id));
  }

  public List<Product> getAllProduct() {
    return Try.of(() -> StreamSupport
            .stream(productRepository.findAll().spliterator(), false)
            .collect(Collectors.toList()))
        .onSuccess(e -> log.info("The list of products was returned"))
        .getOrElseThrow(DatasourceFailureException::new);
  }
}