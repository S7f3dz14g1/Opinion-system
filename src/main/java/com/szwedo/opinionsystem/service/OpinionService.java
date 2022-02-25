package com.szwedo.opinionsystem.service;

import com.szwedo.opinionsystem.entity.Opinion;

import java.util.List;

public interface OpinionService {
  Opinion getOpinionById(Long id);

  List<Opinion> getAllOpinionsByProductId(long productId);

  void removeOpinionById(long id);

  void removeAllOpinions(long productId);

  void updateOpinion(Opinion opinion);

  void addOpinion(Opinion opinion);
}