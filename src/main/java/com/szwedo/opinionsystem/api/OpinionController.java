package com.szwedo.opinionsystem.api;

import com.szwedo.opinionsystem.entity.Opinion;
import com.szwedo.opinionsystem.model.OpinionRequest;
import com.szwedo.opinionsystem.service.OpinionService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.OffsetDateTime;
import java.util.List;

@RestController
@AllArgsConstructor
@RequestMapping("/api/opinion")
public class OpinionController {

  private final OpinionService service;

  @GetMapping(value = "{id}")
  public Opinion getOpinionById(@PathVariable("id") Long id) {
    return service.getOpinionById(id);
  }

  @GetMapping("all/{id}")
  public List<Opinion> getAllOpinions(@PathVariable("id") long productId) {
    return service.getAllOpinionsByProductId(productId);
  }

  @DeleteMapping("/{id}")
  public void removeOpinionById(@PathVariable("id") long id) {
    service.removeOpinionById(id);
  }

  @DeleteMapping("all/{id}")
  public void removeAllOpinions(@PathVariable("id") long productId) {
    service.removeAllOpinions(productId);
  }

  @PostMapping("update")
  public void updateOpinion(@Valid @RequestBody OpinionRequest opinionRequest) {
    service.updateOpinion(Opinion.builder()
        .opinionId(opinionRequest.opinionId())
        .author(opinionRequest.author())
        .content(opinionRequest.content())
        .productId(opinionRequest.productId())
        .stars(opinionRequest.stars())
        .createdDate(OffsetDateTime.now())
        .build());
  }

  @PostMapping("")
  public void addOpinion(@Valid @RequestBody OpinionRequest opinionRequest) {
    service.addOpinion(Opinion.builder()
        .opinionId(opinionRequest.opinionId())
        .author(opinionRequest.author())
        .content(opinionRequest.content())
        .productId(opinionRequest.productId())
        .stars(opinionRequest.stars())
        .createdDate(OffsetDateTime.now())
        .build());
  }
}