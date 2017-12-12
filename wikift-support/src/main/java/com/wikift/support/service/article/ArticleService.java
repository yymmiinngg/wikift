package com.wikift.support.service.article;

import com.wikift.model.article.ArticleEntity;

import java.util.List;

public interface ArticleService {

    /**
     * 保存文章信息
     *
     * @param entity 文章信息
     * @return 保存的文章信息
     */
    ArticleEntity save(ArticleEntity entity);

    /**
     * 更新文章信息
     *
     * @param entity 文章信息
     * @return 文章信息
     */
    ArticleEntity update(ArticleEntity entity);

    /**
     * 查询所有文章
     *
     * @return 文章列表
     */
    List<ArticleEntity> findAll();

    /**
     * 根据文章ID查询文章信息
     *
     * @param id 文章ID
     * @return 文章信息
     */
    ArticleEntity info(Long id);

    /**
     * 删除文章
     *
     * @param id 文章ID
     * @return
     */
    Long delete(Long id);

}
