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

import com.wikift.model.article.ArticleTagEntity;
import com.wikift.model.counter.CounterEntity;
import com.wikift.support.repository.article.ArticleTagRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "articleTagService")
public class ArticleTagServiceImpl implements ArticleTagService {

    @Autowired
    private ArticleTagRepository articleTagRepository;

    @Override
    public Page<ArticleTagEntity> findAll(Pageable pageable) {
        return articleTagRepository.findAll(pageable);
    }

    @Override
    public List<CounterEntity> getAllByArticlesCounterAndTop(Long top) {
        List<CounterEntity> counters = new ArrayList<>();
        articleTagRepository.findAllByArticlesCounterAndTop(top).forEach(v -> {
            counters.add(new CounterEntity(v[0], v[1]));
        });
        return counters;
    }

    @Override
    public ArticleTagEntity getByTitle(String name) {
        return articleTagRepository.findByName(name);
    }

}
