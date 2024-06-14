package com.btistudy.board.repository;

import com.btistudy.board.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

/* 할일: @RepositoryRestResource 를 넣어서 spring rest data 를 준비하고
        (Ex03_2 에서도 똑같이 주기)

        service 탭에 있는 'BoardApplicstion' 실행하고
        브라우저에 localhost:8080/api 치면 HAL Explorer 켜질거임

 */
@RepositoryRestResource /* yaml 파일에서 detection-strategy: annotated 대응 하는 어노테이션 */
public interface Ex03_1_ArticleRepository_api_테스트 extends JpaRepository<Article, Long> {

}
