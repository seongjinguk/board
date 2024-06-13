package com.btistudy.board.repository;

import com.btistudy.board.domain.Ex01_1_Article_엔티티로_등록;
import org.springframework.data.jpa.repository.JpaRepository;

// TDD 할때 사용할 임시 파일임 (이거 이용해서 DB 접근할거임)
public interface Ex01_4_ArticleRepository extends JpaRepository<Ex01_1_Article_엔티티로_등록, Long> {
}

/*  TDD 하러 가기
    - Ctrl + Shift + T 하기
    - Junit5 버전 확인 하기
 */
