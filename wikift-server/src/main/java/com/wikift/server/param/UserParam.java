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
import com.wikift.support.validate.user.UserNameExists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class UserParam {

    @NotEmpty(message = "用户名不能为空")
    @UserNameExists
    private String username;

    @NotEmpty(message = "用户密码不能为空")
    @Size(min = 8, max = 20, message = "密码不能少于8位, 且不能大于20位")
    private String password;

    @NotNull(message = "用户确认密码不能为空")
    @Size(min = 8, max = 20, message = "密码不能少于8位, 且不能大于20位")
    private String repassword;

    @NotNull(message = "用户邮箱不能为空")
    @Email
    @UserEmailExists
    private String email;

}
