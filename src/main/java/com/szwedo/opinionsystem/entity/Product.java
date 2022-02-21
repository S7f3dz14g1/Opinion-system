package com.szwedo.opinionsystem.entity;

import lombok.*;

import javax.persistence.*;

@Entity(name = "product")
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Product {

  @Id
  @SequenceGenerator(
      name = "product_sequence",
      sequenceName = "product_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "product_sequence"
  )
  @Column(
      name = "id",
      updatable = false
  )
  private Long id;

  @Column(
      name = "name",
      nullable = false,
      columnDefinition = "TEXT"
  )
  private String name;

  @Column(
      name = "description",
      nullable = false,
      columnDefinition = "TEXT"
  )
  private String description;

  @Column(
      name = "price",
      nullable = false
  )
  private Double price;
}