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
package com.wikift.support.service.user;

import com.wikift.model.user.UserEntity;

import java.util.List;

public interface UserService {

    UserEntity save(UserEntity entity);

    UserEntity update(UserEntity entity);

    Long delete(Long id);

    UserEntity findByUsername(String username);

    List<UserEntity> findTopByArticle();

    List<UserEntity> findAllFollowersByUserId(Long userId);

    UserEntity findUserEntityByFollowsExists(Long followUserId, Long coverUserId);

    Integer follow(Long followUserId, Long coverUserId);

    Integer unFollow(Long followUserId, Long coverUserId);

    Integer findFollowCount(Long followUserId);

    Integer findFollowCoverCount(Long followUserId);

}
