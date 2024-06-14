package com.btistudy.board.repository;

import com.btistudy.board.domain.Article;
import org.springframework.data.jpa.repository.JpaRepository;

// TDD 할때 사용할 임시 파일임 (이거 이용해서 DB 접근할거임)
public interface ArticleRepository extends JpaRepository<Article, Long> {
}

/*  TDD 하러 가기
    - Ctrl + Shift + T 하기
    - Junit5 버전 확인 하기
 */
