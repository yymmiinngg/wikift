/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 * <p>
 * http://www.apache.org/licenses/LICENSE-2.0
 * <p>
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.wikift.support.service.article;

import com.wikift.common.enums.OrderEnums;
import com.wikift.model.article.ArticleEntity;
import com.wikift.support.repository.article.ArticleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.List;

@Service(value = "articleService")
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public ArticleEntity save(ArticleEntity entity) {
        return articleRepository.save(entity);
    }

    @Override
    public ArticleEntity update(ArticleEntity entity) {
        return articleRepository.save(entity);
    }

    @Override
    public Page<ArticleEntity> findAll(OrderEnums order, Pageable pageable) {
        switch (order) {
            case VIEW:
                return articleRepository.findAllOrderByViewCount(pageable);
            case FABULOU:
                return articleRepository.findAllOrderByFabulouCount(pageable);
            case NATIVE_CREATE_TIME:
            default:
                return articleRepository.findAllOrderByCreateTime(pageable);
        }
    }

    @Override
    public Page<ArticleEntity> getAllArticleBySpace(Long spaceId, Pageable pageable) {
        return articleRepository.findAllBySpace(spaceId, pageable);
    }

    @Override
    public Page<ArticleEntity> getMyArticles(Long userId, Pageable pageable) {
        return articleRepository.findAllToUserAndCreateTime(userId, pageable);
    }

    @Override
    public ArticleEntity getArticle(Long id) {
        return articleRepository.findById(id);
    }

    @Override
    public Long delete(Long id) {
        articleRepository.delete(id);
        return id;
    }

    @Override
    public List<ArticleEntity> findTopByUserEntityAndCreateTime(String username) {
        return articleRepository.findTopByUserEntityAndCreateTime(username);
    }

    @Override
    public Integer fabulousArticle(Integer userId, Integer articleId) {
        return articleRepository.fabulousArticle(userId, articleId);
    }

    @Override
    public Integer unFabulousArticle(Integer userId, Integer articleId) {
        return articleRepository.unFabulousArticle(userId, articleId);
    }

    @Override
    public Integer fabulousArticleExists(Integer userId, Integer articleId) {
        return articleRepository.findFabulousArticleExists(userId, articleId);
    }

    @Override
    public Integer fabulousArticleCount(Integer articleId) {
        return articleRepository.findFabulousArticleCount(articleId);
    }

    @Override
    public Integer viewArticle(Integer userId, Integer articleId, Integer viewCount, String viewDevice) {
        // 查询是否当前设备是否存在于数据库中
        Integer deviceViewCount = articleRepository.findViewArticleByDevice(userId, articleId, viewDevice);
        if (!ObjectUtils.isEmpty(deviceViewCount) && deviceViewCount > 0) {
            // 如果该设备的数据存在数据库中, 则将设备原有数据与现有数据增加
            viewCount = deviceViewCount + viewCount;
            return articleRepository.updateViewArticle(userId, articleId, viewCount, viewDevice);
        }
        return articleRepository.viewArticle(userId, articleId, viewCount, viewDevice);
    }

    @Override
    public Integer viewArticleCount(Integer userId, Integer articleId) {
        return articleRepository.findViewArticle(userId, articleId);
    }

}
