package com.btistudy.board.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;

/** 여기서는 gradle 방식으로 코드를 짜볼거임
 * JPA나 Lombok 같은 디팬던시들을 이용해서 코드를 짤거임.
 *
 * JPA 에너테이션을 이용해서 이 클래스를 엔티티로 바꿔볼거임.
 *
 *  = JPA =
 *      JPA 진영의 ORM(Object Relational Mapping) 기술 표준
 *      Entity 를 분석하고, create나 insert 같은 sql 쿼리를 생성해주고, JDBC api 를 사용해서 DB 접근까지 해줌.
 *      (객체와 테이블을 맵핑패줌)
 *      
 *      Entity = 객체 = DB테이블에 대응하는 하나의 클래스
 * */

/* 
    @Entity 등록할때 반드시 필드중에 어떤게 PK 인지 명시해줘야함
    @Id : 해당 필드가 PK다 라고 선언하는 애너테이션임.
    
    @Table: 엔티티와 매핑할 테이블을 지정하고, 생략 시 매핑할 엔티티 이름을 테이블 이름으로 사용
        ex) @Index(name="원하는 명칭", columlist = "DB에서 사용할 컬럼명")

    @Index: 데이터베이스 인덱스는 추가 쓰기 같은 저장공간을 미리 잡아놓는 에너테이션.
            테이블에 대한 검색 속도를 향상 시키는 데이터 구조
            * @Entity와 세트로 사용해야함
 */
@Getter
@ToString
//@EntityListeners(AuditingEntityListener.class)
//@Entity
@Table(indexes = {
        @Index(columnList = "title"),
        @Index(columnList = "hashtag"),
        @Index(columnList = "createdAt"),
        @Index(columnList = "createdBy"),
})
public class Ex01_1_Article_엔티티로_등록 {
    /* private Long id 의 경우 auto_increment 처럼 DB에 값을 저장하고 그 이후에 키 값을 구할건데 이걸 기본키 전략 이라고 함 */
    @Id /* 기본 키(PK) 와 객체의 필드를 매핑시켜주는 어노테이션
            @Id 만 사용할경우: 키 직접 할당 해야함
            기본키 값을 직접 할당하지 않고 DB가 대신 생성해주는 값을 사용하려면 @GeneratedValue 사용해야함
        */
    @GeneratedValue(strategy = GenerationType.IDENTITY) // 이게 auto_increment 임
                                                        // mysql auto_increment 는 IDENTITY 방식으로 만들어짐

    /* @Setter도 @Getter 처럼 클래스 단위로 걸수 있는데 그렇게 하면 모든 필드에 접근이 가능해진다.
    * 그런데 id나 메타데이터 같은 필드들은 다른 사람이 건드리지 않고 JPA 자동으로 세팅해주는 정보들이기 때문에
    * @Setter 를 클래스 단위에 걸지 않고, 별도로 필요한 필드들에만 @Setter 를 달아주는걸 추천함. */
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
    @ToString.Exclude /* 엄청 중요!!
        이거 없으면 Circle reference 이슈가 생김.
        @ToString이 모든 필드들을 다 찍고 Set<Ex01_2_ArticleComment_엔티티로_등록>꺼를 찍으려고 하는데
        그러면 ArticleComment파일 가서도 거기있는 @ToString이 모든 원소들을 다 찍으려고 하면서 Ex01_1_Article_엔티티로_등록 이라는걸 보는 순간 다시 Article의 @ToString이 동작하면서 또 모든 원소들을 찍을거임. 이런식으로 서로가 서로를 호출하면서 순환 참조를 하게 되면서 메모리가 터져서 시스템이 다운될 수 있다.
        
        그래서 @ToString 끊기 위해서 @ToString.Exclude 를 사용함

    */
    private final Set<Ex01_2_ArticleComment_엔티티로_등록> articleComment = new LinkedHashSet<>();



    /*
        jpa auditing: JPA 에서 자동으로 셋팅하게 해줄때 사용하는 설정
                     이걸 하려면 config 별도로 있어야 함
                     (config 패키지 만들고 JpaConfig 클래스 만들기)
     */

    // 메타데이터
    @CreatedDate @Column(nullable = false) private LocalDateTime createdAt; // 생성일시
    @CreatedBy @Column(nullable = false, length = 100) private String createdBy; // 생성자
    @LastModifiedDate @Column(nullable = false) private LocalDateTime modifiedAt; // 수정일시
    @LastModifiedBy @Column(nullable = false, length = 100) private String modifiedBy; // 수정자

    /* 이렇게 어노테이션만 붙여주기만 해도 auditing이 작동한다.
        @CreatedDate: 최초에 insert 할때 자동으로 한번 넣어준다
        @CreatedBy: 최초에 insert 할때 자동으로 한번 넣어준다
        @LastModifiedDate: 작성 당시의 시간을 매번 실시간으로 넣어준다
        @LastModifiedBy: 작성 당시의 이름을 매번 실시간으로 넣어준다

        위 4개중에 생성일시나 수정일시는 알아낼수 있는데, 최초의 생성자는 테스트시 로그인을 하고 온게 아니기 때문에 따로 알아낼수가 없다.
        그래서 config > JpaConfig 의 public AuditorAware<String> auditorAware() {} 를 사용한다
        리턴타입이 String이기 때문에 createdBy 나 modifiedBy 에 들어가질수 있다.
     */

    protected Ex01_1_Article_엔티티로_등록() {
    }

    /* 사용자가 입력하는 값만 받는 생성자 생성. 나머지 메타 들은 시스템이 알아서 하게 해줘야 함 */
    private Ex01_1_Article_엔티티로_등록(String title, String content, String hashtag) {
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

    public static Ex01_1_Article_엔티티로_등록 of(String title, String content, String hashtag) {
        return new Ex01_1_Article_엔티티로_등록(title, content, hashtag);
    }

    @Override
    public boolean equals(Object o) { // 동등성 비교
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ex01_1_Article_엔티티로_등록 article = (Ex01_1_Article_엔티티로_등록) o;
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















