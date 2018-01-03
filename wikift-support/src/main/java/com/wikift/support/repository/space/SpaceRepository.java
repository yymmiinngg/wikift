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
package com.wikift.support.repository.space;

import com.wikift.model.space.SpaceEntity;
import com.wikift.model.user.UserEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface SpaceRepository extends PagingAndSortingRepository<SpaceEntity, Long> {

    /**
     * 查询所有的公开空间
     *
     * @param pageable 分页信息
     * @return 空间列表
     */
    Page<SpaceEntity> findAllByPrivatedFalse(Pageable pageable);

    /**
     * 根据空间编码获取空间信息
     *
     * @param code 空间编码
     * @return 空间信息
     */
    SpaceEntity findByCode(String code);

    /**
     * 查询所有公开, 用户自己创建的空间
     *
     * @param entity 用户信息
     * @return 空间列表
     */
    List<SpaceEntity> findAllByPrivatedFalseOrUser(UserEntity entity);

    /**
     * 查询用户所有空间
     *
     * @param entity 用户信息
     *               * @param pageable 分页信息
     * @return 空间列表
     */
    Page<SpaceEntity> findAllByUser(UserEntity entity, Pageable pageable);

    /**
     * 查询用户所有公开空间
     *
     * @param entity   用户信息
     * @param pageable 分页信息
     * @return 空间列表
     */
    Page<SpaceEntity> findAllByUserAndPrivatedFalse(UserEntity entity, Pageable pageable);

    /**
     * 查询用户所有私有空间
     *
     * @param entity   用户信息
     * @param pageable 分页信息
     * @return 空间列表
     */
    Page<SpaceEntity> findAllByUserAndPrivatedTrue(UserEntity entity, Pageable pageable);
}
