package com.szwedo.opinionsystem.api;

import com.szwedo.opinionsystem.entity.Opinion;
import com.szwedo.opinionsystem.service.OpinionService;
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
class OpinionControllerTest {

  @Mock
  private  OpinionService service;

  @InjectMocks
  private  OpinionController controller;

  @Test
  void should_return_opinion_when_opinion_exists(){
    //given
    long opinionId=1;
    Optional<Opinion>optionalOpinion=Optional.of(
        Opinion.builder()
            .opinionId(opinionId)
            .content("someContent")
            .author("someAuthor")
            .productId(1L)
            .stars(3)
            .createdDate(OffsetDateTime.now())
            .build());
    //when
    when(service.getOpinionById(opinionId)).thenReturn(optionalOpinion.get());
    //then
    assertEquals(optionalOpinion.get(),controller.getOpinionById(opinionId));
  }

  @Test
  void should_return_opinion_of_lists(){
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
    when(service.getAllOpinionsByProductId(productId)).thenReturn(opinions);
    //then
    assertEquals(opinions,controller.getAllOpinions(productId));
  }
}