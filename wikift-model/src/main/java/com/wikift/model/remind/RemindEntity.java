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
package com.wikift.model.remind;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.wikift.model.article.ArticleEntity;
import com.wikift.model.user.UserEntity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "remind")
@EntityListeners(value = AuditingEntityListener.class)
public class RemindEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "r_id")
    private Long id;

    @Column(name = "r_title")
    private String title;

    @Column(name = "r_content")
    private String content;

    @Column(name = "r_create_time")
    @CreatedDate
    private Date createTime;

    @Column(name = "r_deleted")
    private Boolean deleted = false;

    @Column(name = "r_read")
    private Boolean read = false;

    @Column(name = "r_read_time")
    private Date readTime;

    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.REMOVE})
    @JoinTable(name = "remind_type_relation",
            joinColumns = @JoinColumn(name = "rtr_remind_id"),
            inverseJoinColumns = @JoinColumn(name = "rtr_remind_type_id"))
    @JsonBackReference
    private RemindTypeEntity remindType;

    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "remind_users_relation",
            joinColumns = @JoinColumn(name = "rur_remind_id"),
            inverseJoinColumns = @JoinColumn(name = "rur_user_id"))
    @JsonBackReference
    private List<UserEntity> users;

    @OneToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    @JoinTable(name = "remind_article_relation",
            joinColumns = @JoinColumn(name = "rar_remind_id"),
            inverseJoinColumns = @JoinColumn(name = "rar_article_id"))
    @JsonBackReference
    private ArticleEntity article;

}
