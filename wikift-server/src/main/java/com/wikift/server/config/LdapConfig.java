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
package com.wikift.server.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.data.ldap.repository.config.EnableLdapRepositories;
import org.springframework.ldap.core.LdapTemplate;
import org.springframework.ldap.core.support.LdapContextSource;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;

/**
 * LdapConfig <br/>
 * 描述 : LdapConfig <br/>
 * 作者 : qianmoQ <br/>
 * 版本 : 1.0 <br/>
 * 创建时间 : 2018-01-23 下午2:41 <br/>
 * 联系作者 : <a href="mailTo:shichengoooo@163.com">qianmoQ</a>
 */
@Configuration
@EnableLdapRepositories(basePackages = "com.wikift.support.**")
public class LdapConfig {

    @Resource
    private Environment environment;

    @Bean
    public LdapContextSource contextSource() {
        Boolean ldapEnable = Boolean.valueOf(environment.getProperty("wikift.ldap.enable"));
        if (ldapEnable) {
            LdapContextSource contextSource = new LdapContextSource();
            String ldapUrl = environment.getProperty("wikift.ldap.url");
            Assert.notNull(ldapUrl, "ldap url must not null");
            contextSource.setUrl(ldapUrl);
//            environment.getProperty("wikift.com.wikift.support.ldap.ldif");
            String ldapBase = environment.getProperty("wikift.ldap.partition-suffix");
            Assert.notNull(ldapBase, "ldap base dn must not null");
            contextSource.setBase(ldapBase);
            String ldapPrincipal = environment.getProperty("wikift.ldap.principal");
            Assert.notNull(ldapPrincipal, "ldap principal must not null");
            contextSource.setUserDn(ldapPrincipal);
            String ldapPrincipalPassword = environment.getProperty("wikift.ldap.principal.password");
            Assert.notNull(ldapPrincipalPassword, "ldap principal password must not null");
            contextSource.setPassword(ldapPrincipalPassword);
            return contextSource;
        }
        // TODO: 此处返回默认配置, 后期自主处理
        return null;
    }

    @Bean
    public LdapTemplate ldapTemplate() {
        if (!ObjectUtils.isEmpty(contextSource())) {
            return new LdapTemplate(contextSource());
        }
        return null;
    }

}
