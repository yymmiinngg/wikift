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
import { Component, OnInit, ViewChild } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ArticleService } from '../../../../../services/article.service';
import { ArticleModel } from '../../../../shared/model/article/article.model';
import { ArticleHistoryService } from '../../../../../services/article.history.service';

@Component({
    selector: 'wikift-article-history-constrast',
    templateUrl: 'article.history.constrast.component.html'
})

export class ArticleHistoryConstrastComponent implements OnInit {

    // 文章 ID
    public articleId;
    // 当前对比的文章历史版本
    public articleHistoryVersion;
    // 源文章信息
    public article;
    // 文章历史版本
    public articleHistory;

    constructor(private route: ActivatedRoute,
        private router: Router,
        private articleService: ArticleService,
        private articleHistoryService: ArticleHistoryService) {
        this.route.params.subscribe((params) => {
            this.articleId = params.id;
            this.articleHistoryVersion = params.articleHistoryVersion;
        });
    }

    ngOnInit() {
        // window.location.reload();
        this.initArticleInfomation();
        this.initArticleHistoryInfomation();
    }

    initArticleInfomation() {
        const params = new ArticleModel();
        params.id = this.articleId;
        this.articleService.info(params).subscribe(
            result => {
                this.article = result.data;
            }
        );
    }

    initArticleHistoryInfomation() {
        this.articleHistoryService.getHistoryInfomation(this.articleId, this.articleHistoryVersion).subscribe(
            result => {
                this.articleHistory = result.data;
            }
        );
    }

}
