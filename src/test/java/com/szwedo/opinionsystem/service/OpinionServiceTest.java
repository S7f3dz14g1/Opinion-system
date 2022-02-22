package com.szwedo.opinionsystem.service;

import com.szwedo.opinionsystem.dao.OpinionRepository;
import com.szwedo.opinionsystem.entity.Opinion;
import com.szwedo.opinionsystem.exception.DatasourceFailureException;
import com.szwedo.opinionsystem.exception.OpinionNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class OpinionServiceTest {

  @InjectMocks
  private OpinionService service;
  @Mock
  private OpinionRepository repository;

  @Test
  void should_return_opinion_when_opinion_exists(){
    //given
    long opinionId=1;
    Opinion expectedOpinion=Opinion.builder()
            .opinionId(opinionId)
            .content("someContent")
            .author("someAuthor")
            .productId(1L)
            .stars(3)
            .createdDate(OffsetDateTime.now())
            .build();
    //when
    when(repository.findById(opinionId)).thenReturn(Optional.of(expectedOpinion));
    //then
    assertEquals(expectedOpinion,service.getOpinionById(opinionId));
  }

  @Test
  void should_throw_exception_when_the_server_is_not_responding() {
    //given
    long productId = 1;
    //when
    when(repository.findById(productId)).thenThrow(DatasourceFailureException.class);
    //then
    assertThrows(DatasourceFailureException.class, () -> {
      service.getOpinionById(productId);
    });
  }

  @Test
  void should_throw_OpinionNotFoundException_when_opinion_does_not_exist() {
    //given
    long productId = 1;
    //when
    when(repository.findById(productId)).thenReturn(Optional.empty());
    //then
    assertThrows(OpinionNotFoundException.class, () -> {
      service.getOpinionById(productId);
    });
  }

  @Test
  void should_return_opinion_of_list(){
    //given
    long productId=1L;
    List<Opinion> opinions=List.of(
        Opinion.builder()
            .opinionId(1L)
            .content("someContent")
            .author("someAuthor")
            .productId(1L)
            .stars(3)
            .createdDate(OffsetDateTime.now())
            .build());
    //when
    when(repository.getAllOpinionByProductId(productId)).thenReturn(opinions);
    //then
    assertEquals(opinions,service.getAllOpinionsByProductId(productId));
  }
}