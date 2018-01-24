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
package com.wikift.job.async;

import com.wikift.model.article.ArticleEntity;
import com.wikift.model.remind.RemindEntity;
import com.wikift.model.remind.RemindTypeEntity;
import com.wikift.model.user.UserEntity;
import com.wikift.support.service.article.ArticleService;
import com.wikift.support.service.remind.RemindService;
import com.wikift.support.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 异步消息发送
 *
 * @author qianmoQ
 */
@Service(value = "ramindAsyncJob")
public class RamindAsyncJob {

    @Autowired
    private UserService userService;

    @Autowired
    private RemindService remindService;

    /**
     * 发送消息到当前文章创建者的所有关注用户
     *
     * @param entity 当前创建的文章
     */
    @Async
    public void sendRamindToUserFollows(ArticleEntity entity) {
        // 查询所有关注当前创建文章的用户列表
        List<UserEntity> covers = userService.findAllCoversByUserId(entity.getUser().getId());
        if (covers != null && covers.size() > 0) {
            RemindEntity remind = new RemindEntity();
            remind.setId(0L);
            remind.setArticle(entity);
            remind.setUsers(covers);
            RemindTypeEntity type = new RemindTypeEntity();
            type.setId(2L);
            remind.setRemindType(type);
            remind.setTitle("文章通知");
            remind.setContent(String.format("用户 %s 创建了 %s 文章", entity.getUser().getUsername(), entity.getTitle()));
            remindService.save(remind);
        }
    }

}
