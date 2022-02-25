package com.szwedo.opinionsystem.service;

import com.szwedo.opinionsystem.dao.OpinionRepository;
import com.szwedo.opinionsystem.dao.ProductRepository;
import com.szwedo.opinionsystem.entity.Opinion;
import com.szwedo.opinionsystem.exception.DatasourceFailureException;
import com.szwedo.opinionsystem.exception.OpinionNotFoundException;
import com.szwedo.opinionsystem.exception.ProductNotFoundException;
import io.vavr.control.Try;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Slf4j
@AllArgsConstructor
public class OpinionServiceImpl implements OpinionService {

  private final OpinionRepository opinionRepository;
  private final ProductRepository productRepository;

  @Override
  public Opinion getOpinionById(Long id) {
    return Try.of(() -> opinionRepository.findById(id))
        .onSuccess(e -> log.info("The opinion with id {} was returned", id))
        .onFailure(e -> log.warn("The opinion with id {} does not found", id))
        .getOrElseThrow(DatasourceFailureException::new)
        .orElseThrow(() -> new OpinionNotFoundException(id));
  }

  @Override
  public List<Opinion> getAllOpinionsByProductId(long productId) {
    return Try.of(() -> StreamSupport
            .stream(opinionRepository.getAllOpinionByProductId(productId).spliterator(), false)
            .collect(Collectors.toList()))
        .onSuccess(e -> log.info("The list of products was returned"))
        .getOrElseThrow(DatasourceFailureException::new);
  }

  @Override
  public void removeOpinionById(long id) {
    Try.of(() -> opinionRepository.findById(id))
        .onSuccess(e -> {
          opinionRepository.deleteById(id);
          log.info("The opinion with id {} was removed", id);
        }).getOrElseThrow(DatasourceFailureException::new)
        .orElseThrow(() -> new OpinionNotFoundException(id));
  }

  @Override
  public void removeAllOpinions(long productId) {
    Try.of(() -> productRepository.findById(productId))
        .onSuccess(e -> {
          opinionRepository.deleteAllByProductId(productId);
          log.info("Opinions of list product with id {} was removed", productId);
        })
        .getOrElseThrow(DatasourceFailureException::new)
        .orElseThrow(() -> new ProductNotFoundException(productId));
  }

  @Override
  public void updateOpinion(Opinion opinion) {
    Try.of(() -> productRepository.findById(opinion.getProductId()))
        .onSuccess(e -> {
          opinionRepository.save(opinion);
          log.info("The list of products was returned");
        })
        .getOrElseThrow(DatasourceFailureException::new);
  }

  @Override
  public void addOpinion(Opinion opinion) {
    Try.of(() -> productRepository.findById(opinion.getProductId()))
        .onSuccess(e -> {
          opinionRepository.save(opinion);
          log.info("The opinion with id {} was updated", opinion.getOpinionId());
        })
        .getOrElseThrow(DatasourceFailureException::new);
  }
}