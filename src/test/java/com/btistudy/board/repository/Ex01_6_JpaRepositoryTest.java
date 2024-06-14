package com.btistudy.board.repository;

import com.btistudy.board.config.Ex01_3_JpaConfig;
import com.btistudy.board.domain.Article;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest // 슬라이스 테스트를 할수 있게 해주는 어노테이션.
             // 슬라이스 테스트를 할때 우리가 수동으로 만든 JpaConfig 파일을 읽어오지는 않기 때문에
             // 요 아래 @Import 어노테이션을 이용해서 해당 파일 정보를 읽어올수 있게 해야함
@Import(Ex01_3_JpaConfig.class) // 테스트 파일에서 JPA Auditing 구성 정보 알아보게 하기
class Ex01_6_JpaRepositoryTest {

//    @Autowired Ex01_4_ArticleRepository articleRepository;
//    @Autowired Ex01_5_ArticleCommentRepository articleCommentRepository;

    // 생성자 주입
    ArticleRepository articleRepository;
    ArticleCommentRepository articleCommentRepository;

    public Ex01_6_JpaRepositoryTest(@Autowired ArticleRepository articleRepository, @Autowired ArticleCommentRepository articleCommentRepository) {
        this.articleRepository = articleRepository;
        this.articleCommentRepository = articleCommentRepository;
    }
    /* - 트랜잭션시 사용하는 메서드
        사용법: repository이름.메서드(Sort.by(정렬기준, "기준 컬럼명"))
            1) findAll() - 모든 컬럼을 조회. 페이지 가능
                           당연히 select 작업을 하지만, 잠깐 사이에 해당 테이블에 어떤 변화가 있는지 알수없다.
                           그래서 항상 select 하기 전에 update 를 하고 동작 시켜야 함

            2) findById() - 한번에 한건에 대한 데이터 조회
                            primary key 로 조회

            3) save() - 저장(insert, update)
            4) count() - 레코드 개수 뽑을때 사용
            5) delete() - 삭제(delete)
     */
    /* select 테스트 */
    @DisplayName("select 테스트")
    @Test
    void selectTest() {
        /* given_when_then 패턴 만들고 테스트 하기 (단축코드 만들기)
            세팅(Ctrl + Alt + S) 에서 live templates 검색
            + 버튼 > Template group 으로 그룹 생성하고(이름은 맘대로)
            + 버튼 > Live Template 로 단축키 설정하기
            하단에 Abbreviation 이름 넣기 (ex. gw, gwt)
            template text 영역에 자동완성될 문구 넣기
            맨 아래 'Applicable in java~' 부분의 define 또는 change 눌러서 Java 영역에서
            comment, consumer function, Expression, Statement 체크하기
            그럼 앞으로 gw나 gwt 라고 치면 자동완성 됨
        */




        // given

        // when
        List<Article> articles = articleRepository.findAll();

        // then
        assertThat(articles).isNotNull().hasSize(100);
    }

    /* insert 테스트 */
    @DisplayName("insert 테스트")
    @Test
    void insertTest() {
        // given
        // 기존 카운트 구하기
        long prevCount = articleRepository.count();

        // when
        // 삽입 - DTO에 title, content, hashtag 담아서 넘기기

        Article article = Article.of("제목1", "내용1", "Red");
        articleRepository.save(article);
        System.out.println("asdfsadf: " + articleRepository.save(article));

        // then
        // 현재카운트가 기존 카운트 + 1 이면 테스트 통과
        assertThat(articleRepository.count()).isEqualTo(prevCount + 1);
    }

    /* update 테스트 */
    @DisplayName("update 테스트")
    @Test
    void updateTest() {
        /* 기존 데이터 있어야 하고, 그걸 수정했을때 확인 해야함
            1) 기존의 영속성 컨텍스트 한개 엔티티(객체) 가져오기
            2) 업데이트 - (해쉬태그 업데이트 해보기)
         */

        // given - 테스트를 위해 주어지는 상황
        /* 순서 - 1) 기존의 영속성 컨텍스트 한개 엔티티(객체) 가져오기
            1. 기존의 영속성 컨텍스트로부터 하나 엔티티를 가져올건데 -> articleRepository.findById()
            2. 글번호 1번은 보통 무조건 있으니까 -> findById(1L)
            3. 없으면 throw 시켜서 일단 현재 테스트는 끝나게 하기 -> .orElseThrow()
         */
            Article article = articleRepository.findById(1L).orElseThrow();

        /* 순서 - 2) 업데이트 - (해쉬태그 업데이트 해보기)
            1. 변수 updateHashtag 에 새로 바꿀 해시태그 문자열로 값 저장
            2. 엔티티(article)에 있는 setter 를 이용해서 변수 updateHashtag 에 있는 문자열로 업데이트 하기

         */
            String updateHashtag = "Blue"; // 변수에 업데이트할 문자열 넣고
            article.setHashtag(updateHashtag); // 엔티티에 있는 setter 를 이용해서 변수를 업데이트 하기

        // when - 테스트 해야하는 내용
        /*
            영속성 컨텍스트로부터 가져온 데이터를 그냥 save만 하고 아무것도 안하고 끝내버리면 어짜피 롤백됨.
            테스트를 돌리면 Run탭에 마지막 메세지는 select 구문이 나온다.
            그래서 save 하고 flush 해줘서 해당 필요한 구문까지만 나오게 하기

            * saveAndFlush: 바로 DB에 적용하는 방식

            Flush란 push 같은거
            - flush 동작 과정
                1. 변경점 감지
                2. 수정된 Entity(article) 를 지연 sql 저장소에 등록
                3. 쓰기 지연 sql 저장소의 쿼리를 DB에 전송 (등록, 수정, 삭제 쿼리)
         */
//        Article savedArticle = articleRepository.save(article);
        Article savedArticle = articleRepository.saveAndFlush(article);

        // then
        // savedArticle 이 "hashtag" 필드를 가지고 있는데, 그 필드에 updateHashtag 값이 있는지 확인해라
        assertThat(savedArticle).hasFieldOrPropertyWithValue("hashtag", updateHashtag);
    }

    /* delete 테스트 */
    @DisplayName("delete 테스트")
    @Test
    void deleteTest() {
        /* 기존에 데이터 있어야 되고
            값을 하나 꺼내서 지우기

            1) 기존의 영속성 컨테스트로부터 엔티티 꺼내오기
            2) 지우면 DB 개수 하나 줄어드는 거니까 미리 엔티티 개수(count) 구하기
            3) 하나 삭제
            4) 2번에서 구한 개수 - 1한거랑 새로 현재 구한 count랑 비교해서 같은면 통과
         */

        // given
        /* 1) 기존의 영속성 컨테스트로부터 엔티티 꺼내오기 */
        Article article = articleRepository.findById(1L).orElseThrow();

        /* 2) 지우면 DB 개수 하나 줄어드는 거니까 미리 엔티티 개수(count) 구하기
             게시글(articleRepository)뿐만 아니라 연관된 댓글(articleCommentRepository) 까지 삭제할거라서 두개 개수를 다 뽑아야함
        */
            long prevArticleCount = articleRepository.count();
            long prevArticleCommentCount =articleCommentRepository.count(); // 1000

            int deleteCommentSize = article.getArticleComment().size(); // 10

        // when
            articleRepository.delete(article);

        // then
        // 현재 게시글 개수 (articleRepository.count()) 가 아까 구한 prevArticleCount 보다 1 적으면 테스트 통과 라는 뜻
        assertThat(articleRepository.count()).isEqualTo(prevArticleCount - 1);

        // 현재 게시글의 대한 댓글 개수
        assertThat(articleCommentRepository.count()).isEqualTo(prevArticleCommentCount - deleteCommentSize);

    }
}

















