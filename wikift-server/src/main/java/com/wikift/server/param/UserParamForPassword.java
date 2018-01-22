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
package com.wikift.server.param;

import com.wikift.support.validate.user.UserEmailExists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;

/**
 * UserParamForEmail <br/>
 * 描述 : UserParamForEmail <br/>
 * 作者 : qianmoQ <br/>
 * 版本 : 1.0 <br/>
 * 创建时间 : 2018-01-19 下午6:06 <br/>
 * 联系作者 : <a href="mailTo:shichengoooo@163.com">qianmoQ</a>
 */
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserParamForPassword {

    @NotNull(message = "用户不能为空")
    private Long id;

    @NotNull(message = "用户邮箱不能为空")
    @NotEmpty(message = "用户邮箱不能为空")
    @Email
    private String email;

    @NotNull(message = "用户原密码不能为空")
    @NotEmpty(message = "用户原密码不能为空")
    private String password;

    @NotNull(message = "用户修改的密码不能为空")
    @NotEmpty(message = "用户修改的密码不能为空")
    private String repassword;

}
