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
package com.wikift.support.service.remind;

import com.wikift.model.remind.RemindEntity;
import com.wikift.model.user.UserEntity;
import com.wikift.support.repository.remind.RemindRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "remindService")
public class RemindServiceImpl implements RemindService {

    @Autowired
    private RemindRepository remindRepository;

    @Override
    public RemindEntity save(RemindEntity entity) {
        return remindRepository.save(entity);
    }

    @Override
    public List<RemindEntity> findAll() {
        return (List<RemindEntity>) remindRepository.findAll();
    }

    @Override
    public List<RemindEntity> getAllRemindByUsers(List<UserEntity> users) {
        return remindRepository.findAllByUsers(users);
    }

    @Override
    public List<RemindEntity> getAllUnreadRemindByUsers(List<UserEntity> users) {
        return remindRepository.findAllByUsersAndReadTimeIsNull(users);
    }

}
