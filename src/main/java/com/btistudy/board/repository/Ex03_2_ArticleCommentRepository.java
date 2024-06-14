package com.btistudy.board.repository;

import com.btistudy.board.domain.ArticleComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource
public interface Ex03_2_ArticleCommentRepository extends JpaRepository<ArticleComment, Long> {
}
