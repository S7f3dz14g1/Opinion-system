package com.szwedo.opinionsystem.api;

import com.szwedo.opinionsystem.entity.Opinion;
import com.szwedo.opinionsystem.model.OpinionRequest;
import com.szwedo.opinionsystem.service.OpinionService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


@ExtendWith(MockitoExtension.class)
class OpinionControllerTest {

  @Mock
  private OpinionService service;

  @InjectMocks
  private OpinionController controller;

  @Test
  void should_return_opinion_when_opinion_exists() {
    //given
    long opinionId = 1;
    Optional<Opinion> optionalOpinion = Optional.of(
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
    assertEquals(optionalOpinion.get(), controller.getOpinionById(opinionId));
  }

  @Test
  void should_return_opinion_of_lists() {
    //given
    long productId = 1L;
    List<Opinion> opinions = List.of(
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
    assertEquals(opinions, controller.getAllOpinions(productId));
  }

  @Test
  void should_add_option() {
    //given
    ArgumentCaptor<Opinion> argument = ArgumentCaptor.forClass(Opinion.class);
    OpinionRequest request = OpinionRequest.builder()
        .opinionId(1L)
        .content("content")
        .author("Author")
        .productId(1L)
        .stars(5)
        .build();
    //when
    controller.addOpinion(request);
    //then
    verify(service).addOpinion(argument.capture());
    assertEquals(1, argument.getValue().getOpinionId());
    assertEquals("content", argument.getValue().getContent());
    assertEquals("Author", argument.getValue().getAuthor());
    assertEquals(1, argument.getValue().getProductId());
    assertEquals(5, argument.getValue().getStars());
  }

  @Test
  void should_update_opinion() {
    //given
    ArgumentCaptor<Opinion> argument = ArgumentCaptor.forClass(Opinion.class);
    OpinionRequest request = OpinionRequest.builder()
        .opinionId(1L)
        .content("content")
        .author("Author")
        .productId(1L)
        .stars(5)
        .build();
    //when
    controller.updateOpinion(request);
    //then
    verify(service).updateOpinion(argument.capture());
    assertEquals(1, argument.getValue().getOpinionId());
    assertEquals("content", argument.getValue().getContent());
    assertEquals("Author", argument.getValue().getAuthor());
    assertEquals(1, argument.getValue().getProductId());
    assertEquals(5, argument.getValue().getStars());
  }

  @Test
  void should_remove_all_opinion() {
    //given
    long productId = 10L;
    ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
    //when
    controller.removeAllOpinions(productId);
    //then
    verify(service).removeAllOpinions(argument.capture());
    assertEquals(argument.getValue(), productId);
  }

  @Test
  void should_remove_opinion_by_product_id() {
    //given
    long productId = 10L;
    ArgumentCaptor<Long> argument = ArgumentCaptor.forClass(Long.class);
    //when
    controller.removeOpinionById(productId);
    //then
    verify(service).removeOpinionById(argument.capture());
    assertEquals(argument.getValue(), productId);
  }
}