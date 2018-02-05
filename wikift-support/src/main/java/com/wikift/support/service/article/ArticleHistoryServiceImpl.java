package com.wikift.support.service.article;

import com.wikift.model.article.ArticleEntity;
import com.wikift.model.article.ArticleHistoryEntity;
import com.wikift.support.repository.article.ArticleHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "articleHistoryService")
public class ArticleHistoryServiceImpl implements ArticleHistoryService {

    @Autowired
    private ArticleHistoryRepository articleHistoryRepository;

    @Override
    public ArticleHistoryEntity save(ArticleHistoryEntity entity) {
        return articleHistoryRepository.save(entity);
    }

    @Override
    public List<ArticleHistoryEntity> getByArticle(ArticleEntity entity) {
        return articleHistoryRepository.findByArticle(entity);
    }

}
