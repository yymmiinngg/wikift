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
package com.wikift.model.space;

import com.wikift.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;

import javax.persistence.*;
import javax.validation.constraints.NotNull;

/**
 * 空间实体
 *
 * @author qianmoQ
 */
@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "space")
public class SpaceEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "s_id")
    private Long id;

    @Column(name = "s_avatar")
    private String avatar;

    @NotNull
    @Column(name = "s_name")
    private String name;

    @NotNull
    @Column(name = "s_code")
    private String code;

    @Column(name = "s_private")
    private Boolean privated = true;

    @Column(name = "s_description")
    private String description;

    @Column(name = "create_time")
    @CreatedDate
    private String createTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "space_users_relation",
            joinColumns = @JoinColumn(name = "sur_space_id"),
            inverseJoinColumns = @JoinColumn(name = "sur_users_id"))
    @NotNull
    private UserEntity user;

}
