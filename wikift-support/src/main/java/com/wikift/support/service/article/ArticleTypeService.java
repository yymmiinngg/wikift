package com.wikift.support.service.article;

import com.wikift.model.article.ArticleTypeEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleTypeService {

    /**
     * 查询所有文章类型
     *
     * @return 文章类型
     */
    Page<ArticleTypeEntity> findAll(Pageable pageable);

}
