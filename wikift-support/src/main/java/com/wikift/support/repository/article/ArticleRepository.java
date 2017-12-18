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
package com.wikift.support.repository.article;

import com.wikift.model.article.ArticleEntity;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ArticleRepository extends PagingAndSortingRepository<ArticleEntity, Long> {

    /**
     * 根据用户和时间查询文章信息
     *
     * @param username 用户id
     * @return 文章列表
     */
    @Query(value = "SELECT * FROM users_article_relation AS uar, article AS a, users AS u " +
            "WHERE uar.uar_user_id = u.u_id " +
            "AND uar.uar_article_id = a.a_id " +
            "AND DATE_SUB(CURDATE() , INTERVAL 7 DAY) <= date(a.a_create_time) " +
            "AND u.u_username = ?1", nativeQuery = true)
    List<ArticleEntity> findTopByUserEntityAndCreateTime(String username);

    /**
     * 赞文章
     *
     * @param userId    当前赞用户id
     * @param articleId 当前被赞文章id
     * @return 状态
     */
    @Modifying
    @Query(value = "INSERT INTO users_article_fabulous_relation(uafr_user_id, uafr_article_id) " +
            "VALUE(?1, ?2)",
            nativeQuery = true)
    Integer fabulousArticle(Integer userId, Integer articleId);

    /**
     * 解除赞文章
     *
     * @param userId    当前解除赞用户id
     * @param articleId 当前解除赞文章id
     * @return 状态
     */
    @Modifying
    @Query(value = "DELETE FROM users_article_fabulous_relation " +
            "WHERE uafr_user_id = ?1 " +
            "AND uafr_article_id = ?2",
            nativeQuery = true)
    Integer unFabulousArticle(Integer userId, Integer articleId);

    /**
     * 检查当前文章是否被当前用户赞
     *
     * @param userId    当前赞用户id
     * @param articleId 当前被赞文章id
     * @return 数据总数
     */
    @Query(value = "SELECT count(1) AS count FROM users_article_fabulous_relation " +
            "WHERE uafr_user_id = ?1 " +
            "AND uafr_article_id = ?2",
            nativeQuery = true)
    Integer findFabulousArticleExists(Integer userId, Integer articleId);

}
