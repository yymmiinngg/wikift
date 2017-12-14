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
package com.wikift.support.repository.user;

import com.wikift.model.user.UserEntity;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface UserRepository extends CrudRepository<UserEntity, Long> {

    /**
     * 根据用户名称查找用户
     *
     * @param username 用户名
     * @return 用户信息
     */
    UserEntity findByUsername(String username);

    /**
     * 根据文章数量查询用户排行榜
     *
     * @return 用户排行榜
     */
    @Query(value = "SELECT DISTINCT(uar.uar_user_id), COUNT(uar.uar_user_id) AS u_count, u.u_id, u.u_username, u.u_password, u.u_avatar, u.u_alias_name, u.u_signature " +
            "FROM users_article_relation AS uar LEFT JOIN users AS u ON uar.uar_user_id = u.u_id GROUP BY uar.uar_user_id ORDER BY u_count DESC LIMIT 10",
            nativeQuery = true)
    List<UserEntity> findTopByArticle();

}
