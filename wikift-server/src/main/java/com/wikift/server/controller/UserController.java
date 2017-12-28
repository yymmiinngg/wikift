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

import com.wikift.common.enums.MessageEnums;
import com.wikift.common.utils.ShaUtils;
import com.wikift.model.result.CommonResult;
import com.wikift.model.user.UserEntity;
import com.wikift.server.param.UserParam;
import com.wikift.support.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    CommonResult<UserEntity> register(@RequestBody @Validated UserParam param) {
        Assert.notNull(param, MessageEnums.PARAMS_NOT_NULL.getValue());
        if (!param.getPassword().equals(param.getRepassword())) {
            return CommonResult.validateError(MessageEnums.PARAMS_CONTRAST_VALIDATE_ERROR);
        }
        // 手动设置id为0, 阻止jpa存储数据出现json绑定错误
        UserEntity entity = new UserEntity();
        entity.setId(0l);
        entity.setUsername(param.getUsername());
        entity.setPassword(ShaUtils.hash256(param.getPassword()));
        return CommonResult.success(userService.save(entity));
    }

    @PreAuthorize("hasAuthority(('USER'))")
    @RequestMapping(value = "/info/{username}", method = RequestMethod.GET)
    CommonResult<UserEntity> info(@PathVariable(value = "username") String username) {
        Assert.notNull(username, MessageEnums.PARAMS_NOT_NULL.getValue());
        return CommonResult.success(userService.getInfoByUsername(username));
    }

    @PreAuthorize("hasAuthority(('USER'))")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    CommonResult<UserEntity> update(@RequestBody UserEntity entity) {
        Assert.notNull(entity, MessageEnums.PARAMS_NOT_NULL.getValue());
        UserEntity targetUserEntity = userService.getInfoByUsername(entity.getUsername());
        entity.setPassword(targetUserEntity.getPassword());
        return CommonResult.success(userService.update(entity));
    }

    @RequestMapping(value = "/top", method = RequestMethod.GET)
    CommonResult<UserEntity> findTopByArticle() {
        return CommonResult.success(userService.findTopByArticle());
    }

    @PreAuthorize("hasAuthority(('USER'))")
    @RequestMapping(value = "/follow", method = RequestMethod.PUT)
    CommonResult follow(@RequestBody UserEntity entity) {
        Assert.notNull(entity, MessageEnums.PARAMS_NOT_NULL.getValue());
        return CommonResult.success(userService.follow(entity.getId(), entity.getFollows().get(0).getId()));
    }

    @PreAuthorize("hasAuthority(('USER'))")
    @RequestMapping(value = "/unfollow", method = RequestMethod.PUT)
    CommonResult unfollow(@RequestBody UserEntity entity) {
        Assert.notNull(entity, MessageEnums.PARAMS_NOT_NULL.getValue());
        return CommonResult.success(userService.unFollow(entity.getId(), entity.getFollows().get(0).getId()));
    }

    @PreAuthorize("hasAuthority(('USER'))")
    @RequestMapping(value = "/follows/{userId}", method = RequestMethod.GET)
    CommonResult<UserEntity> getFollows(@PathVariable(value = "userId") Long userId) {
        Assert.notNull(userId, MessageEnums.PARAMS_NOT_NULL.getValue());
        return CommonResult.success(userService.findAllFollowersByUserId(userId));
    }

    @PreAuthorize("hasAuthority(('USER'))")
    @RequestMapping(value = "/follows/check", method = RequestMethod.GET)
    CommonResult<UserEntity> checkFollow(@RequestParam(value = "followUserId") Long followUserId,
                                         @RequestParam(value = "coverUserId") Long coverUserId) {
        Assert.notNull(followUserId, MessageEnums.PARAMS_NOT_NULL.getValue());
        Assert.notNull(coverUserId, MessageEnums.PARAMS_NOT_NULL.getValue());
        return CommonResult.success(userService.findUserEntityByFollowsExists(followUserId, coverUserId));
    }

    @PreAuthorize("hasAuthority(('USER'))")
    @RequestMapping(value = "/follows/count", method = RequestMethod.GET)
    CommonResult<UserEntity> findFollowCount(@RequestParam(value = "followUserId") Long followUserId) {
        Assert.notNull(followUserId, MessageEnums.PARAMS_NOT_NULL.getValue());
        Map<String, Integer> count = new ConcurrentHashMap<>();
        count.put("followCount", userService.findFollowCount(followUserId));
        count.put("coverCount", userService.findFollowCoverCount(followUserId));
        return CommonResult.success(count);
    }

}