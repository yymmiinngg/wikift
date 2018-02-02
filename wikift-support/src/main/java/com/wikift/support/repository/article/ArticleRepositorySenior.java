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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * ArticleRepositorySenior <br/>
 * 描述 : 文章数据库高级操作 <br/>
 * 作者 : qianmoQ <br/>
 * 版本 : 1.0 <br/>
 * 创建时间 : 2018-01-29 下午5:22 <br/>
 * 联系作者 : <a href="mailTo:shichengoooo@163.com">qianmoQ</a>
 */
@Repository
public class ArticleRepositorySenior {

    @PersistenceContext
    private EntityManager entityManager;

    public Page<ArticleEntity> search(Long tagId, String articleTitle, Long spaceId, Long userId, Pageable pageable) {
        StringBuffer bufferPrefix = new StringBuffer();
        // 默认查询的数据信息
        bufferPrefix.append("SELECT a.a_id, a.a_title, a.a_content, a.a_create_time, IFNULL(SUM(uavr.uavr_view_count) , 0) AS view_count, IFNULL(COUNT(DISTINCT uafr.uafr_user_id), 0) AS fabulou_count, uar.uar_user_id, atr.atr_article_type_id, sar.sar_space_id, IFNULL(c.comments_count, 0) AS comments_count ");
        bufferPrefix.append("FROM article AS a ");

        StringBuffer bufferJoin = new StringBuffer();
        bufferJoin.append("LEFT OUTER JOIN users_article_relation AS uar ON a.a_id = uar.uar_article_id ");
        bufferJoin.append("LEFT OUTER JOIN article_type_relation AS atr ON a.a_id = atr.atr_article_id ");
        bufferJoin.append("LEFT OUTER JOIN users_article_view_relation AS uavr ON a.a_id = uavr.uavr_article_id ");
        bufferJoin.append("LEFT OUTER JOIN users_article_fabulous_relation AS uafr ON a.a_id = uafr.uafr_article_id ");
        bufferJoin.append("LEFT OUTER JOIN space_article_relation AS sar ON a.a_id = sar.sar_article_id ");
        bufferJoin.append("LEFT OUTER JOIN space AS s ON s.s_id = sar.sar_space_id ");
        bufferJoin.append("LEFT OUTER JOIN (SELECT car.car_article_id, count(car.car_comments_id) AS comments_count FROM comments_article_relation AS car GROUP BY car.car_article_id ) AS c ON a.a_id = c.car_article_id ");
        bufferJoin.append("LEFT OUTER JOIN article_tag_relation AS atr1 ON a.a_id = atr1.atr_article_id ");
        bufferJoin.append("WHERE s.s_private = FALSE ");
        // 根据标签查询
        if (!ObjectUtils.isEmpty(tagId) && tagId > 0) {
            bufferJoin.append("AND atr1.atr_article_tag_id = :tagId ");
        }
        // 根据文章名称查询
        if (!StringUtils.isEmpty(articleTitle)) {
            bufferJoin.append("AND a.a_title LIKE :articleTitle ");
        }
        // 根据空间查询
        if (!ObjectUtils.isEmpty(spaceId) && spaceId > 0) {
            bufferJoin.append("AND sar.sar_space_id = :spaceId ");
        }
        // 根据用户查询
        if (!ObjectUtils.isEmpty(userId) && userId > 0) {
            bufferJoin.append("AND uar.uar_user_id = :userId ");
        }
        
        StringBuffer bufferGroup = new StringBuffer();
        bufferGroup.append("GROUP BY a_id, uar.uar_user_id, atr.atr_article_type_id, sar.sar_space_id ");
        
        StringBuffer bufferOrder = new StringBuffer();
        bufferOrder.append("ORDER BY SUM(uavr.uavr_view_count) DESC ");

        StringBuffer bufferLimit = new StringBuffer();
        bufferLimit.append("LIMIT :page, :pageSize");

        StringBuffer bufferQuery = new StringBuffer();
        bufferQuery.append(bufferPrefix);
        bufferQuery.append(bufferJoin);
        bufferQuery.append(bufferGroup);
        bufferQuery.append(bufferOrder);
        bufferQuery.append(bufferLimit);

        Query query = entityManager.createNativeQuery(bufferQuery.toString(), ArticleEntity.class);
        joinParam(tagId, articleTitle, spaceId, userId, query);
        query.setParameter("page", pageable.getPageNumber());
        query.setParameter("pageSize", pageable.getPageSize());

        StringBuffer bufferCount = new StringBuffer();
        bufferCount.append("SELECT COUNT(DISTINCT a.a_id) FROM article AS a ");
        bufferCount.append(bufferJoin);
        Query countQuery = entityManager.createNativeQuery(bufferCount.toString());
        joinParam(tagId, articleTitle, spaceId, userId, countQuery);
        int count = Integer.valueOf(countQuery.getResultList().get(0).toString());
        return new PageImpl<>(query.getResultList(), pageable, count);
    }

    private void joinParam(Long tagId, String articleTitle, Long spaceId, Long userId, Query query) {
        // 根据标签查询
        if (!ObjectUtils.isEmpty(tagId) && tagId > 0) {
            query.setParameter("tagId", tagId);
        }
        // 根据文章名称查询
        if (!StringUtils.isEmpty(articleTitle)) {
            query.setParameter("articleTitle", "%" + articleTitle + "%");
        }
        // 根据空间查询
        if (!ObjectUtils.isEmpty(spaceId) && spaceId > 0) {
            query.setParameter("spaceId", spaceId);
        }
        // 根据用户查询
        if (!ObjectUtils.isEmpty(userId) && userId > 0) {
            query.setParameter("userId", userId);
        }
    }

}
