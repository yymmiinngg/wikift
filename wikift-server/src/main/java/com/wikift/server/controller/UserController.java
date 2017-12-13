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
import com.wikift.model.result.CommonResult;
import com.wikift.model.user.UserEntity;
import com.wikift.support.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PreAuthorize("hasAuthority(('USER'))")
    @RequestMapping(value = "/info/{username}", method = RequestMethod.GET)
    CommonResult<UserEntity> info(@PathVariable(value = "username") String username) {
        Assert.notNull(username, MessageEnums.PARAMS_NOT_NULL.getValue());
        return CommonResult.success(userService.findByUsername(username));
    }

    @PreAuthorize("hasAuthority(('USER'))")
    @RequestMapping(value = "/update", method = RequestMethod.PUT)
    CommonResult<UserEntity> update(@RequestBody UserEntity entity) {
        Assert.notNull(entity, MessageEnums.PARAMS_NOT_NULL.getValue());
        UserEntity targetUserEntity = userService.findByUsername(entity.getUsername());
        entity.setPassword(targetUserEntity.getPassword());
        return CommonResult.success(userService.update(entity));
    }

}