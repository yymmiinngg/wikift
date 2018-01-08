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
package com.wikift.support.service.space;

import com.wikift.model.space.SpaceEntity;
import com.wikift.model.user.UserEntity;
import com.wikift.support.repository.space.SpaceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "spaceService")
public class SpaceServiceImpl implements SpaceService {

    @Autowired
    private SpaceRepository spaceRepository;

    @Override
    public Page<SpaceEntity> getAllPublicSpace(Pageable pageable) {
        return spaceRepository.findAllByPrivatedFalse(pageable);
    }

    @Override
    public SpaceEntity createSpace(SpaceEntity entity) {
        return spaceRepository.save(entity);
    }

    @Override
    public SpaceEntity getSpaceInfoByCode(String code) {
        return spaceRepository.findByCode(code);
    }

    @Override
    public SpaceEntity getSpaceInfoById(Long id) {
        return spaceRepository.findOne(id);
    }

    @Override
    public Page<SpaceEntity> getAllSpaceByPrivatedFalseOrUser(UserEntity entity, Pageable pageable) {
        return spaceRepository.findAllByPrivatedFalseOrUser(entity, pageable);
    }

    @Override
    public Page<SpaceEntity> getAllSpaceByUser(UserEntity entity, Pageable pageable) {
        return spaceRepository.findAllByUser(entity, pageable);
    }

    @Override
    public Page<SpaceEntity> getAllPublicSpaceByUser(UserEntity entity, Pageable pageable) {
        return spaceRepository.findAllByUserAndPrivatedFalse(entity, pageable);
    }

    @Override
    public Page<SpaceEntity> getAllPrivateSpaceByUser(UserEntity entity, Pageable pageable) {
        return spaceRepository.findAllByUserAndPrivatedTrue(entity, pageable);
    }

    @Override
    public Long getArticleCountById(Long spaceId) {
        return spaceRepository.findArticleCountById(spaceId);
    }

}
