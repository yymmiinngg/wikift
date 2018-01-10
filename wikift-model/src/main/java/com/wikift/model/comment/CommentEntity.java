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
package com.wikift.model.comment;

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

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "comments")
@EntityListeners(value = AuditingEntityListener.class)
public class CommentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "c_id")
    private Long id;

    @Column(name = "c_content")
    private String content;

    @Column(name = "create_time")
    @CreatedDate
    private Date createTime;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "comments_article_relation",
            joinColumns = @JoinColumn(name = "car_comments_id"),
            inverseJoinColumns = @JoinColumn(name = "car_article_id"))
//    @JsonBackReference
    private ArticleEntity article;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(name = "comments_users_relation",
            joinColumns = @JoinColumn(name = "cur_comments_id"),
            inverseJoinColumns = @JoinColumn(name = "cur_users_id"))
//    @JsonBackReference
    private UserEntity user;

}
