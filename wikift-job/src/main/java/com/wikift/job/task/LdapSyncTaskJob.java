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
package com.wikift.job.task;

import com.wikift.common.utils.ReflectUtils;
import com.wikift.model.user.UserEntity;
import com.wikift.model.user.UserTypeEntity;
import com.wikift.support.ldap.model.LdapUserModel;
import com.wikift.support.ldap.service.LdapUserService;
import com.wikift.support.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.text.SimpleDateFormat;

/**
 * LDAP 同步用户任务 <br/>
 * 描述 : 主要用于同步LDAP用户到wikift系统中 <br/>
 * 作者 : qianmoQ <br/>
 * 版本 : 1.0 <br/>
 * 创建时间 : 2018-01-24 下午1:07 <br/>
 * 联系作者 : <a href="mailTo:shichengoooo@163.com">qianmoQ</a>
 */
@Component
public class LdapSyncTaskJob {

    @Resource
    private Environment environment;

    @Autowired
    private LdapUserService ldapUserService;

    @Autowired
    private UserService userService;

    private final String default_email = "email";
    private final String[] default_username = new String[]{"name", "cn", "sn"};

    @Scheduled(fixedRate = 300000)
    public void syncLdapToSystem() {
        Boolean ldapEnable = Boolean.valueOf(environment.getProperty("wikift.ldap.enable"));
        if (ldapEnable) {
            SimpleDateFormat format = new SimpleDateFormat("YYYY-mm-DD HH:mm:ss");
            String emailField = environment.getProperty("wikift.ldap.reflect.attributes.email");
            String usernameField = environment.getProperty("wikift.ldap.reflect.attributes.username");
            ldapUserService.findAll().forEach(v -> {
                UserEntity user = new UserEntity();
                UserTypeEntity userType = new UserTypeEntity();
                userType.setId(2L);
                user.setUserType(userType);
                // 如果没有配置email映射使用默认
                if (ObjectUtils.isEmpty(emailField) || emailField.equalsIgnoreCase(default_email)) {
                    user.setEmail(v.getEmail());
                } else {
                    user.setEmail(String.valueOf(ReflectUtils.getFieldValue(emailField, v)));
                }
                // 如果没有配置name映射使用默认
                if (ObjectUtils.isEmpty(usernameField) || constain(usernameField)) {
                    user.setUsername(getUserName(v));
                } else {
                    user.setUsername(String.valueOf(ReflectUtils.getFieldValue(usernameField, v)));
                }
                // 如果同步的username为空的话, 不做数据入库操作
                if (!StringUtils.isEmpty(user.getUsername())) {
                    UserEntity source = userService.getInfoByUsername(user.getUsername());
                    // 当前用户不存在数据库中, 进行入库操作
                    if (ObjectUtils.isEmpty(source)) {
                        userService.save(user);
                        // TODO: 记录入库数据
                    } else {
                        // 如果用户存在数据库中抽取数据不一致的email(如果原email为空)
                        if (StringUtils.isEmpty(source.getEmail()) && !StringUtils.isEmpty(user.getEmail())) {
                            source.setEmail(user.getEmail());
                            userService.save(user);
                        }
                    }
                    // TODO: 记录未入库数据
                }
            });
        }
    }

    private Boolean constain(String filed) {
        for (String s : default_username) {
            if (filed.equalsIgnoreCase(s)) {
                return true;
            }
        }
        return false;
    }

    private String getUserName(LdapUserModel userModel) {
        for (String s : default_username) {
            Object result = ReflectUtils.getFieldValue(s, userModel);
            if (ObjectUtils.isEmpty(result)) {
                continue;
            }
            return String.valueOf(result);
        }
        return null;
    }

}
