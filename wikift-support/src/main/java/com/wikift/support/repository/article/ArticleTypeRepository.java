package com.wikift.support.repository.article;

import com.wikift.model.article.ArticleTypeEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ArticleTypeRepository extends PagingAndSortingRepository<ArticleTypeEntity, Long> {
}
