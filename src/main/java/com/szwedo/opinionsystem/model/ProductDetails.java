package com.szwedo.opinionsystem.model;

import com.szwedo.opinionsystem.entity.Opinion;
import com.szwedo.opinionsystem.entity.Product;
import lombok.Builder;

import java.util.List;

public record ProductDetails(Product product,
                             double average,
                             List<Opinion> opinions) {

  @Builder
  public ProductDetails {
  }
}