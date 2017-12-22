package com.wikift.support.service.article;

import com.wikift.model.article.ArticleTagEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface ArticleTagService {

    /**
     * 查询所有文章标签
     *
     * @return 文章标签列表
     */
    Page<ArticleTagEntity> findAll(Pageable pageable);

}
