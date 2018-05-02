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
package com.wikift.server.controller;

import com.wikift.common.utils.BeanUtils;
import com.wikift.common.utils.MessageUtils;
import com.wikift.common.utils.PageAndSortUtils;
import com.wikift.model.article.ArticleEntity;
import com.wikift.model.comment.CommentEntity;
import com.wikift.model.result.CommonResult;
import com.wikift.server.param.CommentParam;
import com.wikift.support.service.comment.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(value = "${wikift.api.path}")
public class CommentController {

    @Autowired
    private CommentService commentService;

    @RequestMapping(value = "public/comment/list", method = RequestMethod.GET)
    CommonResult getAllCommentByArticle(@RequestParam(value = "page", defaultValue = "0") Integer page,
                                        @RequestParam(value = "size", defaultValue = "10") Integer size,
                                        @RequestParam(value = "articleId") Long articleId) {
        Assert.notNull(page, MessageUtils.getParamNotNull("page"));
        Assert.notNull(size, MessageUtils.getParamNotNull("size"));
        Assert.notNull(articleId, MessageUtils.getParamNotNull("articleId"));
        ArticleEntity entity = new ArticleEntity();
        entity.setId(articleId);
        return CommonResult.success(commentService.getAllCommentByArticle(entity, PageAndSortUtils.getPageAndSortAndCreateTimeByDESC(page, size)));
    }

    @PreAuthorize("hasAuthority(('USER'))")
    @RequestMapping(value = "comment/create", method = RequestMethod.POST)
    CommonResult<CommentEntity> createSpace(@RequestBody @Validated CommentParam param) {
        Assert.notNull(param, MessageUtils.getParamNotNull("param"));
        CommentEntity comment = new CommentEntity();
        BeanUtils.copy(param, comment);
        return CommonResult.success(commentService.createComment(comment));
    }

    @RequestMapping(value = "comment/view/{articleId}", method = RequestMethod.GET)
    CommonResult<List> getArticleCommentsByCreateTimeAndTop7(@PathVariable(value = "articleId") Long articleId) {
        Assert.notNull(articleId, MessageUtils.getParamNotNull("articleId"));
        return CommonResult.success(commentService.getArticleCommentsByCreateTimeAndTop7(articleId));
    }

    @PreAuthorize("hasAuthority(('USER')) && hasPermission(#id, 'delete|comment')")
    @RequestMapping(value = "comment/delete/{id}", method = RequestMethod.DELETE)
    CommonResult delete(@PathVariable Long id) {
        Assert.notNull(id, MessageUtils.getParamNotNull("id"));
        return CommonResult.success(commentService.deleteCommentById(id));
    }

}
