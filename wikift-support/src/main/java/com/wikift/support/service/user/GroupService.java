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

import com.wikift.model.user.GroupEntity;

import java.util.List;

public interface GroupService {

    /**
     * 保存组信息
     *
     * @param entity 组信息
     * @return 组信息
     */
    GroupEntity save(GroupEntity entity);

    /**
     * 删除组信息
     *
     * @param entity 组信息
     * @return 删除的组信息
     */
    GroupEntity delete(GroupEntity entity);

    /**
     * 查询所有的组
     *
     * @return 组列表
     */
    List<GroupEntity> findAll();

}
