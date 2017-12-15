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

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.sun.istack.internal.Nullable;
import com.wikift.model.role.RoleEntity;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
// 禁用: 防止 Failed to evaluate Jackson deserialization for type
//@AllArgsConstructor
@RequiredArgsConstructor
@Entity
@Table(name = "users")
@JsonInclude(JsonInclude.Include.NON_EMPTY)
@JsonIgnoreProperties(value = {"password", "userEntity"})
public class UserEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "u_id")
    @NonNull
    private Long id;

    @Column(name = "u_username")
    private String username;

    @Column(name = "u_password")
    private String password;

    @Column(name = "u_avatar")
    private String avatar;

    @Column(name = "u_alias_name")
    private String aliasName;

    @Column(name = "u_signature")
    private String signature;

    @ManyToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "users_role_relation",
            joinColumns = @JoinColumn(name = "urr_user_id", referencedColumnName = "u_id"),
            inverseJoinColumns = @JoinColumn(name = "urr_role_id", referencedColumnName = "r_id"))
    private List<RoleEntity> userRoles;

    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "users_follow_relation",
            joinColumns = @JoinColumn(name = "ufr_user_id_follw"),
            inverseJoinColumns = @JoinColumn(name = "ufr_user_id_cover"))
    @JsonBackReference
    private List<UserEntity> follows;

}
