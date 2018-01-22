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
package com.wikift.support.repository.comment;

import com.wikift.model.article.ArticleEntity;
import com.wikift.model.comment.CommentEntity;
import com.wikift.model.counter.CounterEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Map;

@Transactional
public interface CommentRepository extends PagingAndSortingRepository<CommentEntity, Long> {

    /**
     * 根据文章id获取所有的评论
     *
     * @param entity 文章id
     * @return 该文章评论列表
     */
    Page<CommentEntity> findAllByArticle(ArticleEntity entity, Pageable pageable);

    /**
     * 根据ID查询当前文章最近7天评论走势图
     *
     * @param articleId 文章id
     * @return 7天评论走势图
     */
    @Query(value = "SELECT a.formatDate AS dataKey, IFNULL(b.commentCount, 0) AS dataValue " +
            "FROM ( " +
            "SELECT curdate() AS formatDate UNION ALL " +
            "SELECT date_sub( curdate( ), INTERVAL 1 DAY ) AS formatDate UNION ALL " +
            "SELECT date_sub( curdate( ), INTERVAL 2 DAY ) AS formatDate UNION ALL " +
            "SELECT date_sub( curdate( ), INTERVAL 3 DAY ) AS formatDate UNION ALL " +
            "SELECT date_sub( curdate( ), INTERVAL 4 DAY ) AS formatDate UNION ALL " +
            "SELECT date_sub( curdate( ), INTERVAL 5 DAY ) AS formatDate UNION ALL " +
            "SELECT date_sub( curdate( ), INTERVAL 6 DAY ) AS formatDate " +
            ") AS a LEFT JOIN ( " +
            "SELECT a.a_id , DATE_FORMAT( c.create_time, '%Y-%m-%d' ) AS formatDate, COUNT( car.car_comments_id ) AS commentCount " +
            "FROM article AS a " +
            "LEFT OUTER JOIN comments_article_relation AS car ON a.a_id = car.car_article_id " +
            "LEFT OUTER JOIN comments AS c ON c.c_id = car.car_comments_id " +
            "WHERE a.a_id = ?1 " +
            "GROUP BY formatDate " +
            ") AS b ON a.formatDate = b.formatDate " +
            "ORDER BY a.formatDate",
            nativeQuery = true)
    List<Object[]> findArticleCommentsByCreateTimeAndTop7(Long articleId);

}
