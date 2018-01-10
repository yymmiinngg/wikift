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
package com.wikift.server.param;

import com.wikift.model.article.ArticleEntity;
import com.wikift.model.user.UserEntity;
import com.wikift.support.validate.article.ArticleIsExists;
import com.wikift.support.validate.space.SpaceCodeExists;
import com.wikift.support.validate.user.UserIsExists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class CommentParam {

    @NotEmpty(message = "评论内容不能为空")
    private String content;

    @NotNull(message = "评论者不能为空")
    @UserIsExists
    private UserEntity user;

    @NotNull(message = "请指定评论的文章")
    @ArticleIsExists
    private ArticleEntity article;

}
