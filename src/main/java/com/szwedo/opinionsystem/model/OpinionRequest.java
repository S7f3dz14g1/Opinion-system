package com.szwedo.opinionsystem.model;

import com.sun.istack.NotNull;
import lombok.Builder;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;

public record OpinionRequest(@NotNull Long opinionId,
                             @NotNull Long productId,
                             @NotBlank String author,
                             @NotBlank String content,
                             @Min(value = 1)
                             @Max(value = 5)
                             @NotNull Integer stars) {
  @Builder
  public OpinionRequest {
  }
}
