package com.wikift.support.service.article;

import com.wikift.model.article.ArticleTypeEntity;

public interface ArticleTypeService {

    /**
     * 查询所有文章类型
     *
     * @return 文章类型
     */
    Iterable<ArticleTypeEntity> findAll();

}
