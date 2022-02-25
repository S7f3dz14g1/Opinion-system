package com.szwedo.opinionsystem.entity;

import lombok.*;

import javax.persistence.*;
import java.time.OffsetDateTime;

@Entity(name = "opinion")
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Builder
public class Opinion {

  @Id
  @SequenceGenerator(
      name = "opinion_sequence",
      sequenceName = "opinion_sequence",
      allocationSize = 1
  )
  @GeneratedValue(
      strategy = GenerationType.SEQUENCE,
      generator = "opinion_sequence"
  )
  @Column(
      name = "id",
      nullable = false
  )
  private Long opinionId;

  @Column(
      name = "product_id",
      nullable = false
  )
  private Long productId;

  @Column(
      name = "author",
      nullable = false
  )
  private String author;

  @Column(
      name = "content",
      nullable = false
  )
  private String content;

  @Column(
      name = "created",
      nullable = false
  )
  private OffsetDateTime createdDate;

  @Column(
      name = "stars",
      nullable = false
  )
  private Integer stars;
}
