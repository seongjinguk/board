package com.btistudy.board.repository;

import com.btistudy.board.domain.Article;
import com.btistudy.board.domain.QArticle;
import com.querydsl.core.types.dsl.DateTimeExpression;
import com.querydsl.core.types.dsl.StringExpression;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.querydsl.binding.QuerydslBinderCustomizer;
import org.springframework.data.querydsl.binding.QuerydslBindings;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

// TDD 할때 사용할 임시 파일임 (이거 이용해서 DB 접근할거임)
@RepositoryRestResource
public interface ArticleRepository extends JpaRepository<Article, Long>,
        QuerydslPredicateExecutor<Article>
        , QuerydslBinderCustomizer<QArticle> {

/* QuerydslBinderCustomizer<QArticle> 를 사용하려면 customize 메서드 필요함
    1) Ctrl + O 눌러서  오버라이드 창 열고
    2) 스크롤 내려보면 org.springframewor.data.querydsl.binding
    3) 그 안에 있는 customize(QuerydslBindings bindings, QArticle root) 선택

    * customize: 세부 규칙 재구성 할 수 있는 메서드
                 원래는 여기가 인터페이스라 구현부를 만들수 없는데
                 java 1.8 이후부터는 default 를 써서 가능함

*/

    @Override
    default void customize(QuerydslBindings bindings, QArticle root) {

        bindings.excludeUnlistedProperties(true); // 리스트를 만들건데 그 리스트에 있는 컬럼만 검색되게 해라
                                                  // ex) localhost:8080/api?키=값

        /* 원하는 필드를 추가하는 부분 */
        bindings.including(root.title, root.content,root.hashtag, root.createdAt, root.createdBy);
//            bindings.bind(root.title).first(StringExpression::LikeIgnoreCase); // 쿼리상 "Like '${문자열}'" 로 들어감

        bindings.bind(root.title).first(StringExpression::containsIgnoreCase); // "like '%문자열$'
        bindings.bind(root.content).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.hashtag).first(StringExpression::containsIgnoreCase);
        bindings.bind(root.createdAt).first(DateTimeExpression::eq); // 날짜여서 DateTimeExpression 사용. eq는 equals 라는 뜻
                                                                     // 날짜 필드는 완벽히 같은 것만 검색되도록 할거임
                                                                     // 근데 이렇게 하면 시분초를 다 0으로 인식하기 때문에 조심해야함

        bindings.bind(root.createdBy).first(StringExpression::containsIgnoreCase);
    }
}
