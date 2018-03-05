package com.wikift.support.service.article;

import com.wikift.model.article.ArticleEntity;
import com.wikift.model.article.ArticleHistoryEntity;

import java.util.List;

public interface ArticleHistoryService {

    ArticleHistoryEntity save(ArticleHistoryEntity entity);

    List<ArticleHistoryEntity> getByArticle(ArticleEntity entity);

    ArticleHistoryEntity getByVersionAndArticleId(String version, Long articleId);

    ArticleEntity restoreVersion(String version, Long articleId);

}
