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

import com.wikift.common.enums.RoleEnums;
import com.wikift.model.role.RoleEntity;
import com.wikift.model.user.UserEntity;
import com.wikift.support.repository.role.RoleRepository;
import com.wikift.support.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service(value = "userService")
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Override
    public UserEntity getUserById(Long id) {
        return userRepository.findOne(id);
    }

    @Override
    public UserEntity save(UserEntity entity) {
        // 默认设置用户权限为USER
        List<RoleEntity> roleEntities = new ArrayList<>();
        roleEntities.add(roleRepository.findByRoleName(RoleEnums.USER.name()));
        entity.setUserRoles(roleEntities);
        return userRepository.save(entity);
    }

    @Override
    public UserEntity update(UserEntity entity) {
        entity.setPassword(userRepository.findByUsername(entity.getUsername()).getPassword());
        return userRepository.save(entity);
    }

    @Override
    public Integer updateEmail(UserEntity entity) {
        return userRepository.updateByEmail(entity.getId(), entity.getEmail());
    }

    @Override
    public UserEntity getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public Long delete(Long id) {
        userRepository.delete(id);
        return id;
    }

    @Override
    public UserEntity getInfoByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public List<UserEntity> findTopByArticle() {
        return userRepository.findTopByArticle();
    }

    @Override
    public List<UserEntity> findAllFollowersByUserId(Long userId) {
        return userRepository.findAllFollowersByUserId(userId);
    }

    @Override
    public List<UserEntity> findAllCoversByUserId(Long userId) {
        return userRepository.findAllCoversByUserId(userId);
    }

    @Override
    public UserEntity findUserEntityByFollowsExists(Long followUserId, Long coverUserId) {
        return userRepository.findUserEntityByFollowsExists(followUserId, coverUserId);
    }

    @Override
    public Integer follow(Long followUserId, Long coverUserId) {
        return userRepository.follow(followUserId, coverUserId);
    }

    @Override
    public Integer unFollow(Long followUserId, Long coverUserId) {
        return userRepository.unFollow(followUserId, coverUserId);
    }

    @Override
    public Integer findFollowCount(Long followUserId) {
        return userRepository.findFollowCount(followUserId);
    }

    @Override
    public Integer findFollowCoverCount(Long followUserId) {
        return userRepository.findFollowCoverCount(followUserId);
    }

}
