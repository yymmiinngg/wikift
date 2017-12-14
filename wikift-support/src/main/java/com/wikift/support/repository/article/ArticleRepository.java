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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ArticleRepository extends CrudRepository<ArticleEntity, Long> {

    /**
     * 根据用户和时间查询文章信息
     *
     * @param username 用户名称
     * @return 文章列表
     */
    @Query(value = "SELECT * FROM users_article_relation AS uar, article AS a, users AS u " +
            "WHERE uar.uar_user_id = u.u_id " +
            "AND uar.uar_article_id = a.a_id " +
            "AND DATE_SUB(CURDATE() , INTERVAL 7 DAY) <= date(a.a_create_time) " +
            "AND u.u_username = ?1", nativeQuery = true)
    List<ArticleEntity> findTopByUserEntityAndCreateTime(String username);

}
