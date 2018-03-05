package com.wikift.support.service.article;

import com.wikift.model.article.ArticleEntity;
import com.wikift.model.article.ArticleHistoryEntity;
import com.wikift.support.repository.article.ArticleHistoryRepository;
import com.wikift.support.repository.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service(value = "articleHistoryService")
public class ArticleHistoryServiceImpl implements ArticleHistoryService {

    @Autowired
    private ArticleHistoryRepository articleHistoryRepository;

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public ArticleHistoryEntity save(ArticleHistoryEntity entity) {
        return articleHistoryRepository.save(entity);
    }

    @Override
    public List<ArticleHistoryEntity> getByArticle(ArticleEntity entity) {
        return articleHistoryRepository.findByArticle(entity);
    }

    @Override
    public ArticleHistoryEntity getByVersionAndArticleId(String version, Long articleId) {
        ArticleEntity entity = new ArticleEntity();
        entity.setId(articleId);
        return articleHistoryRepository.findByVersionAndArticle(version, entity);
    }

    @Override
    public ArticleEntity restoreVersion(String version, Long articleId) {
        ArticleEntity entity = articleRepository.findById(articleId);
        ArticleHistoryEntity historyEntity = this.getByVersionAndArticleId(version, articleId);
        entity.setContent(historyEntity.getContent());
        articleRepository.save(entity);
        ArticleHistoryEntity restoreHistory = new ArticleHistoryEntity();
        // 保存还原历史
        restoreHistory.setId(0L);
        restoreHistory.setContent(entity.getContent());
        restoreHistory.setUser(entity.getUser());
        restoreHistory.setArticle(entity);
        restoreHistory.setVersion(String.valueOf(new Date().getTime()));
        articleHistoryRepository.save(restoreHistory);
        return entity;
    }

}
