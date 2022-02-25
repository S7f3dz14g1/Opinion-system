package com.szwedo.opinionsystem.service;

import com.szwedo.opinionsystem.dao.OpinionRepository;
import com.szwedo.opinionsystem.dao.ProductRepository;
import com.szwedo.opinionsystem.entity.Opinion;
import com.szwedo.opinionsystem.entity.Product;
import com.szwedo.opinionsystem.exception.DatasourceFailureException;
import com.szwedo.opinionsystem.exception.OpinionNotFoundException;
import com.szwedo.opinionsystem.exception.ProductNotFoundException;
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
class OpinionServiceImplTest {

  @InjectMocks
  private OpinionServiceImpl service;
  @Mock
  private OpinionRepository opinionRepository;
  @Mock
  private ProductRepository productRepository;

  @Test
  void should_return_opinion_when_opinion_exists() {
    //given
    long opinionId = 1;
    Opinion expectedOpinion = Opinion.builder()
        .opinionId(opinionId)
        .content("someContent")
        .author("someAuthor")
        .productId(1L)
        .stars(3)
        .createdDate(OffsetDateTime.now())
        .build();
    //when
    when(opinionRepository.findById(opinionId)).thenReturn(Optional.of(expectedOpinion));
    //then
    assertEquals(expectedOpinion, service.getOpinionById(opinionId));
  }

  @Test
  void should_throw_exception_when_the_server_is_not_responding() {
    //given
    long productId = 1;
    long opinionId = 1;
    Opinion opinion = Opinion.builder()
        .opinionId(1L)
        .content("someContent")
        .author("someAuthor")
        .productId(1L)
        .stars(3)
        .createdDate(OffsetDateTime.now())
        .build();
    //when
    when(opinionRepository.findById(productId)).thenThrow(OpinionNotFoundException.class);
    when(productRepository.findById(productId)).thenThrow(ProductNotFoundException.class);
    when(opinionRepository.getAllOpinionByProductId(productId)).thenThrow(DatasourceFailureException.class);
    //then
    assertThrows(DatasourceFailureException.class, () -> service.getOpinionById(productId));
    assertThrows(DatasourceFailureException.class, () -> service.getAllOpinionsByProductId(productId));
    assertThrows(DatasourceFailureException.class, () -> service.updateOpinion(opinion));
    assertThrows(DatasourceFailureException.class, () -> service.addOpinion(opinion));
    assertThrows(DatasourceFailureException.class, () -> service.removeOpinionById(opinionId));
    assertThrows(DatasourceFailureException.class, () -> service.removeAllOpinions(productId));
  }

  @Test
  void should_throw_OpinionNotFoundException_when_opinion_does_not_exist() {
    //given
    long productId = 1;
    //when
    when(opinionRepository.findById(productId)).thenReturn(Optional.empty());
    //then
    assertThrows(OpinionNotFoundException.class, () -> {
      service.getOpinionById(productId);
    });
  }

  @Test
  void should_return_opinion_of_list() {
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
    when(opinionRepository.getAllOpinionByProductId(productId)).thenReturn(opinions);
    //then
    assertEquals(opinions, service.getAllOpinionsByProductId(productId));
  }

  @Test
  void should_add_opinion_when_opinion_is_correct() {
    //given
    ArgumentCaptor<Opinion> argument = org.mockito.ArgumentCaptor.forClass(Opinion.class);
    Opinion opinion = Opinion.builder()
        .opinionId(1L)
        .content("content")
        .author("someAuthor")
        .productId(1L)
        .stars(3)
        .createdDate(OffsetDateTime.now())
        .build();
    Optional<Product> product = Optional.of(
        Product.builder()
            .id(1L)
            .description("description")
            .name("name")
            .price(1.2)
            .build()
    );
    //when
    when(productRepository.findById(1L)).thenReturn(product);
    service.addOpinion(opinion);
    //then
    verify(opinionRepository).save(argument.capture());
    assertEquals(1, argument.getValue().getOpinionId());
    assertEquals("content", argument.getValue().getContent());
    assertEquals("someAuthor", argument.getValue().getAuthor());
    assertEquals(1, argument.getValue().getProductId());
    assertEquals(3, argument.getValue().getStars());
  }

  @Test
  void should_remove_option_by_id_when_opinion_exists() {
    //given
    ArgumentCaptor<Long> argumentCaptor = ArgumentCaptor.forClass(Long.class);
    long opinionId = 1;
    Opinion opinion = Opinion.builder()
        .opinionId(1L)
        .content("content")
        .author("someAuthor")
        .productId(1L)
        .stars(3)
        .createdDate(OffsetDateTime.now())
        .build();
    //when
    when(opinionRepository.findById(opinionId)).thenReturn(Optional.of(opinion));
    service.removeOpinionById(opinionId);
    //then
    verify(opinionRepository).deleteById(argumentCaptor.capture());
    assertEquals(opinionId, argumentCaptor.getValue());
  }

  @Test
  void should_throw_opinion_not_found_exception_when_opinion_does_not_exist() {
    //given
    long opinionId = 1;
    //when
    when(opinionRepository.findById(opinionId)).thenReturn(Optional.empty());
    //then
    assertThrows(OpinionNotFoundException.class, () -> {
      service.removeOpinionById(opinionId);
    });
  }

  @Test
  void should_remove_all_opinion_when_product_exists() {
    //given
    long productId=1;
    ArgumentCaptor<Long> argumentCaptor=ArgumentCaptor.forClass(Long.class);
    //when
    opinionRepository.deleteAllByProductId(productId);
    //then
    verify(opinionRepository).deleteAllByProductId(argumentCaptor.capture());
    assertEquals(productId,argumentCaptor.getValue());
  }
  
  @Test
  void should_throw_product_not_found_exception_when_product_does_not_exist(){
    //given
    long productId =1;
    //when
    when(productRepository.findById(productId)).thenReturn(Optional.empty());
    //then
    assertThrows(ProductNotFoundException.class,()->{
      service.removeAllOpinions(productId);
    });
  }
}