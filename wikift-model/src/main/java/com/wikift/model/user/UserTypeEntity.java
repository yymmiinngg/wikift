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
package com.wikift.model.user;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;

/**
 * 用户类型实体 <br/>
 * 描述 : 用户类型实体 <br/>
 * 作者 : qianmoQ <br/>
 * 版本 : 1.0 <br/>
 * 创建时间 : 2018-01-22 下午3:13 <br/>
 * 联系作者 : <a href="mailTo:shichengoooo@163.com">qianmoQ</a>
 */
@Data
@ToString
@NoArgsConstructor
// 禁用: 防止 Failed to evaluate Jackson deserialization for type
//@AllArgsConstructor
@Entity
@Table(name = "users_type")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
public class UserTypeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ut_id")
    private Long id;

    @Column(name = "ut_code")
    private String code;

    @Column(name = "ut_name")
    private String name;

    @Column(name = "ut_active")
    private Boolean active;

    @Column(name = "ut_type")
    private Boolean type;

    @Column(name = "create_time")
    @CreatedDate
    private String createTime;

}
