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
package com.wikift.support.service.comment;

import com.wikift.model.article.ArticleEntity;
import com.wikift.model.comment.CommentEntity;
import com.wikift.model.counter.CounterEntity;
import com.wikift.support.repository.comment.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "commentService")
public class CommentServiceImpl implements CommentService {

    @Autowired
    private CommentRepository commentRepository;

    @Override
    public CommentEntity createComment(CommentEntity entity) {
        return commentRepository.save(entity);
    }

    @Override
    public Page<CommentEntity> getAllCommentByArticle(ArticleEntity entity, Pageable pageable) {
        return commentRepository.findAllByArticle(entity, pageable);
    }

    @Override
    public List<CounterEntity> getArticleCommentsByCreateTimeAndTop7(Long articleId) {
        List<CounterEntity> counters = new ArrayList<>();
        commentRepository.findArticleCommentsByCreateTimeAndTop7(articleId).forEach(v -> {
            counters.add(new CounterEntity(v[0], v[1]));
        });
        return counters;
    }

}
