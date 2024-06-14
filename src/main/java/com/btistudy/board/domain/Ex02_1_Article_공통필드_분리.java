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
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/** Article 과 ArticleComment 에 있는 공통 필드(메타데이터)들이 있는데 별도로 빼서 관리할거임
 *  이유: Article 과 ArticleComment 처럼 FK 키로 엮여있는 테이블들을 만드는 경우
 *       모든 domain 파일들에 중복코드 들이 들어가게 된다. (id, created_by, created_at, modified_by, modified_at)
 *       그래서 별도의 파일에 공통되는 것들을 다 몰아넣고 사용하게 만들어 볼거임
 *
 *       (공통 필드는 회사마다 다 다름)
 *       회사마다 '1엔티티 = 1테이블' 원칙을 선호하는 곳도 있고
 *       지금처럼 간결하게 코드 구성을 선호하는 곳도 있음
 *
 *       추출하는 방법
 *       1) @Embeded - 옛날방식
 *                      공통되는 필드들(id, created_by, created_at, modified_by, modified_at) 을 하나의 클래스로 만들어서
 *                      @Embeded 있는 곳에서 지원하는 방식
 *
 *                      ex) Class Tmp {
 *                              여기에는 created_by, created_at, modified_by, modified_at 다 넣고
 *                          }
 *                          
 *                      @Embeded Tmp t; <- 이렇게 하면 이 코드가 있는 자리에 Tmp 클래스 안에 있는 것들이 들어옴
 *                      
 *       2) @MappedSuperClass - 요즘 방식(요즘 실무에서는 이거씀)
 *                              @MappedSuperClass 어노테이션이 붙은
 *                              1. domain > Ex02_3_AuditingFields 클래스 생성하기
 *                              2. 공통 필드 옮겨가기 (created_by, created_at, modified_by, modified_at)
 * */

@Getter
@ToString
//@EntityListeners(AuditingEntityListener.class) /* auditing 관련된거. Ex02_3 으로 이동해야함 */
//@Entity
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
        /* AuditingFields.java 사용시 @Index 꺼를 보내야 하는데 그렇게 하지 않음.
        * 못보내는건 아닌데 보내려면 세팅 해야할게 너무 많음. 그래서 비 효율적임 */
})
public class Ex02_1_Article_공통필드_분리 extends Ex02_3_AuditingFields {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // 고유번호

    @Setter
    @Column(nullable = false) // 해당 컬럼이 not null인 경우 @Column(nullable = false) 써줘야함(기본값은 true)
    private String title; // 제목 // NN, varchar(255)

    @Setter
    @Column(nullable = false, length = 10000) // content varchar(10000) not null
    private String content; // 본문 // NN, varchar(10000)

    @Setter
    private String hashtag; // 해시태그 // varchar(255)


    // 양방향 바인딩 (Ex01_2 의  // 연관관계 매핑. 다 끝나고 와서 하는거)
    @OrderBy("id") // 양방향 바인딩 할건데 정렬 기준은 id로 하겠다는 뜻
    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL) // mappedBy로 양방향 바인딩의 이름을 지정
    @ToString.Exclude
    private final Set<Ex02_1_Article_공통필드_분리> articleComment = new LinkedHashSet<>();


//    // 메타데이터
//    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
//    @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자
//    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
//    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자

    protected Ex02_1_Article_공통필드_분리() {
    }

    /* 사용자가 입력하는 값만 받는 생성자 생성. 나머지 메타 들은 시스템이 알아서 하게 해줘야 함 */
    private Ex02_1_Article_공통필드_분리(String title, String content, String hashtag) {
        this.title = title;
        this.content = content;
        this.hashtag = hashtag;
    }


    /* factory method pattern (정적 팩토리 메서드)
        정적 팩토리 메서드는 객체 생성의 역할을 하는 클래스 메서드(static 으로 무조건 해야함).
        of 메서드를 이용해서 직접적으로 생성자를 사용해서 객체를 생성함

        장점: 1) static 이기 때문에 new 를 이용하지 않아도 생성자를 만들 수 있다.
             2) return 을 가지고 있기 때문에 상속을 사용할때 값을 확인할 수 있다.(한마디로 하위 자료형 객체를 반환 할 수 있다)
             3) 객체 생성을 캡슐화 가능
     */

    public static Ex02_1_Article_공통필드_분리 of(String title, String content, String hashtag) {
        return new Ex02_1_Article_공통필드_분리(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) { // 동등성 비교
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ex02_1_Article_공통필드_분리 article = (Ex02_1_Article_공통필드_분리) o;
//        return Objects.equals(id, article.id);
        return id!=null && id.equals(article.id);
    }
    
    // 여기 오버라이드 안하면 Objects에 있는거 쓸건데 id 어찌보내줄거임? 
    // 그래서 그냥 여기에 오버라이드 해서 id 보내기
    @Override
    public int hashCode() { // 동일성 비교
        return Objects.hashCode(id);
    }

/*
    equals - 값이 같으면 true
             둘다 null 이어도 true 나옴
    heshcode - 객체를 식별하는 Integer 값
               객체의 값을 특정 알고리즘을 이용해서 계산된 정수값을 지칭함
                heshcode 사용하는 이유는 객체를 비교할때 드는 비용이 낮다
                자바에서 2개의 객체가 같은지 비교할때 equals() 를 쓰는데
                여러개의 객체를 비교할때 equals를 사용하면 Integer 값들을 비교할때 많은 시간과 비용이 발생함
*/
}















