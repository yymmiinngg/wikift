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

import com.wikift.common.utils.MessageUtils;
import com.wikift.model.result.CommonResult;
import com.wikift.model.space.SpaceEntity;
import com.wikift.model.user.UserEntity;
import com.wikift.support.service.space.SpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.util.Assert;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/space")
public class SpaceController {

    @Autowired
    private SpaceService spaceService;

    @PreAuthorize("hasAuthority(('USER'))")
    @RequestMapping(value = "/list", method = RequestMethod.GET)
    CommonResult<SpaceEntity> getAllSpaces() {
        return CommonResult.success(spaceService.getAllSpace());
    }

    @PreAuthorize("hasAuthority(('USER'))")
    @RequestMapping(value = "/list/user/{userId}", method = RequestMethod.GET)
    CommonResult<SpaceEntity> getAllSpaceByPrivatedFalseAndUser(@PathVariable Long userId) {
        Assert.notNull(userId, MessageUtils.getParamNotNull("userId"));
        UserEntity entity = new UserEntity();
        entity.setId(userId);
        return CommonResult.success(spaceService.getAllSpaceByPrivatedFalseOrUser(entity));
    }

    @PreAuthorize("hasAuthority(('USER'))")
    @RequestMapping(value = "/create", method = RequestMethod.POST)
    CommonResult<SpaceEntity> createSpace(@RequestBody @Validated SpaceEntity entity) {
        Assert.notNull(entity, MessageUtils.getParamNotNull("entity"));
        return CommonResult.success(spaceService.createSpace(entity));
    }

}
