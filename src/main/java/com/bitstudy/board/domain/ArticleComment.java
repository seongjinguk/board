package com.bitstudy.board.domain;

import java.time.LocalDateTime;

public class ArticleComment {
    private Long id; // 고유번호

    private Article article;  // 연관관계 매핑.
    /* 연관 관계 없이 코드를 짤거라면
    * private Long FK-id 이런식으로 그냥 하면 됨.
    * Article과 관계를 맺고 있는 필드 라는걸 객체지향적으로 표현한거임*/


    private String content; // 본문


    // 메타데이터
    private LocalDateTime createdAt;  // 생성일시
    private String createdBy;  // 생성자
    private LocalDateTime modifideAt;  // 수정일시
    private String modifiedBy;  // 수정자
}
