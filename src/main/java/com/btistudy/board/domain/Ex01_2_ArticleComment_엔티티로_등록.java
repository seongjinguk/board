package com.btistudy.board.domain;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.Objects;

@Getter
@ToString
//@EntityListeners(AuditingEntityListener.class)
//@Entity
@Table(indexes = {
        @Index(columnList = "content"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
public class Ex01_2_ArticleComment_엔티티로_등록 {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유번호

    @Setter
    @ManyToOne(optional = false)
    private Ex01_1_Article_엔티티로_등록 article; // 연관관계 매핑.
    /* 연관 관계 없이 코드를 짤거라면
        private Long FK_id 이런식으로 그냥 하면 됨.
        private Article article는 Article과 관계를 맺고 있는 필드 라는걸 객체지향적으로 표현한거임
        그러기 위해서 Article과의 연관관계(현재 파일 기준)를 명시해줘야함 (@ManyToOne)
        이건 필수값이다 라는 뜻으로 (optional = false) 를 걸어줘야함
        댓글은 여러개:게시글1개 이기 때문에 단방향 바인딩 이기 때문에
        Article 에서도 바인딩을 해서 양방향 바인딩으로 만들어줘야함
    */

    @Setter
    @Column(nullable = false, length = 500)
    private String content; // 본문



    // 메타데이터
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자


    protected Ex01_2_ArticleComment_엔티티로_등록() {
    }

    private Ex01_2_ArticleComment_엔티티로_등록(Ex01_1_Article_엔티티로_등록 article, String content) {
        this.content = content;
        this.article = article;
    }

    public static Ex01_2_ArticleComment_엔티티로_등록 of(Ex01_1_Article_엔티티로_등록 article, String content) {
        return new Ex01_2_ArticleComment_엔티티로_등록(article, content);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ex01_2_ArticleComment_엔티티로_등록 that = (Ex01_2_ArticleComment_엔티티로_등록) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    
    /* Ex01_1, Ex01_2, Ex01_3 다 하고 어플리케이션(BoardApplication.java) 실행 해보기
        그냥 실행하면 Run 탭에서 동작함(원래 방식)
        그런데 이걸 Service 탭에서 실행시킬수도 있음.
        방법: 
            1) 서비스탭 열고(alt + 8)
            2) 좌측 상단 + 버튼 누르기
            3) Run configration 선택
            4) 스크롤 맨 아래쯤 'spring boot' 있음 그거 클릭

        이거 하는 이유: Run탭에서 빌드작업을 하거나 테스트를 하게 되면 실행 로그랑 서비스로그를 분리해서 볼수 있음
     */
}


















