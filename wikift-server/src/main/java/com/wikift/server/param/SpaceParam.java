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

import com.wikift.model.user.UserEntity;
import com.wikift.support.validate.space.SpaceCodeExists;
import com.wikift.support.validate.user.UserIsExists;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class SpaceParam {

    @NotEmpty(message = "空间图像不能为空")
    private String avatar;

    @NotEmpty(message = "空间名称不能为空")
    private String name;

    @NotNull(message = "创建空间需要必须同意空间协议")
    @AssertTrue(message = "创建空间需要必须同意空间协议")
    private Boolean agreement = false;

    @NotEmpty(message = "空间编码不能为空")
    @Pattern(regexp = "[a-zA-Z]+", message = "空间编码只能是字母, 不能出现其他字符")
    @SpaceCodeExists
    private String code;

    @NotNull(message = "请先指定创建空间的类型")
    private Boolean privated = true;

    private String description;

    @NotNull(message = "空间创建者不能为空")
    @UserIsExists
    private UserEntity user;

}
