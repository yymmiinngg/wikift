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

import com.wikift.model.article.ArticleTagEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;

import javax.transaction.Transactional;
import java.util.List;

@Transactional
public interface ArticleTagRepository extends PagingAndSortingRepository<ArticleTagEntity, Long> {

    /**
     * 查询前?排行的标签
     *
     * @param top 排行数
     * @return 标签集
     */
    @Query(value = "SELECT ats.at_title AS dataKey, COUNT(atr.atr_article_id) AS dataValue " +
            "FROM article_tag AS ats " +
            "LEFT OUTER JOIN article_tag_relation AS atr ON ats.at_id = atr.atr_article_tag_id " +
            "GROUP BY dataKey " +
            "ORDER BY dataValue DESC " +
            "LIMIT ?1",
            nativeQuery = true)
    List<Object[]> findAllByArticlesCounterAndTop(Long top);

    /**
     * 根据标签名称查询标签信息
     *
     * @param title 标签名称
     * @return 标签信息
     */
    ArticleTagEntity findByTitle(String title);

}
