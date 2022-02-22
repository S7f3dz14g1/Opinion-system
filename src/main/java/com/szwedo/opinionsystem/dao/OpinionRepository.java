package com.szwedo.opinionsystem.dao;

import com.szwedo.opinionsystem.entity.Opinion;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import javax.transaction.Transactional;
import java.util.List;

@Repository
@Transactional
public interface OpinionRepository extends CrudRepository<Opinion, Long> {

  @Query(value = "SELECT u FROM opinion u WHERE u.productId = ?1")
  List<Opinion> getAllOpinionByProductId(Long productId);

  @Modifying
  @Query(value = "DELETE FROM opinion u WHERE u.productId=  ?1")
  void deleteAllByProductId(Long productId);
}
