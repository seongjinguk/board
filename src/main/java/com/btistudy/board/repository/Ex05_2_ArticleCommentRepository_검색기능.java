package com.btistudy.board.repository;

import com.btistudy.board.domain.Article;
import com.btistudy.board.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface Ex05_2_ArticleCommentRepository_검색기능 extends
        JpaRepository<ArticleComment, Long>
        , QuerydslPredicateExecutor<ArticleComment> {


}
