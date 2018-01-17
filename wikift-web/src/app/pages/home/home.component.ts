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
import { Subscription } from 'rxjs/Subscription';

import { ArticleModel } from '../../shared/model/article/article.model';
import { UserModel } from '../../shared/model/user/user.model';
import { CommonResultModel } from '../../shared/model/result/result.model';

import { CookieUtils } from '../../shared/utils/cookie.util';

import { CommonConfig } from '../../../config/common.config';

import { ArticleService } from '../../../services/article.service';
import { UserService } from '../../../services/user.service';
import { CommonPageModel } from '../../shared/model/result/page.model';
import { OrderEnumModel } from '../../shared/model/enum/order.enum.model';
import { ChartsUtils } from '../../shared/utils/chart.echarts.utils';
import { ChartWordCloudModel } from '../../shared/model/chart/chart.wordcloud.model';
import { ArticleTagService } from '../../../services/article.tag.service';

@Component({
    selector: 'wikift-home',
    templateUrl: 'home.component.html'
})

export class HomeComponent implements OnInit {

    public articles;
    // 活跃用户
    public activeUsers;
    // 分页数据
    public page: CommonPageModel;
    // 当前页数
    public currentPage: number;
    // 当前时间
    public currentDay = new Date().getTime();
    public loadArticleBusy: Subscription;
    // 排序规则
    public order;
    // 抽取layout布局的用户信息
    public userInfo;
    // 字符云图表
    public wordCloudChart;

    constructor(private articleService: ArticleService,
        private userService: UserService,
        private articleTagService: ArticleTagService) {
        this.page = new CommonPageModel();
        this.userInfo = CookieUtils.getUser();
    }

    ngOnInit() {
        this.initArticleList(this.page);
        this.initTopUserByActive();
        this.initArticleTagTop();
    }

    /**
     * 文章列表
     */
    initArticleList(page: CommonPageModel) {
        page.number = 0;
        page.size = 10;
        this.loadArticleBusy = this.articleService.list(page).subscribe(
            result => {
                this.articles = result.data.content;
                this.page = CommonPageModel.getPage(result.data);
                this.currentPage = this.page.number;
            }
        );
    }

    initTopUserByActive() {
        this.userService.topByActive().subscribe(
            result => { this.activeUsers = result.data; }
        );
    }

    initArticleTagTop() {
        this.articleTagService.top().subscribe(
            result => {
                this.wordCloudChart = ChartsUtils.gerenateWordCloudChart(true, ChartWordCloudModel.generateArrayByResponse(result));
            }
        );
    }

    loadArticleOrderByCreateTime() {
        this.order = OrderEnumModel.NATIVE_CREATE_TIME;
        this.page.order = this.order;
        this.initArticleList(this.page);
    }

    loadArticleOrderByView() {
        this.order = OrderEnumModel.VIEW;
        this.page.order = this.order;
        this.initArticleList(this.page);
    }

    loadArticleOrderByFabulou() {
        this.order = OrderEnumModel.FABULOU;
        this.page.order = this.order;
        this.initArticleList(this.page);
    }

    loadMyArticle() {
        this.page.number = 0;
        this.page.size = 10;
        this.loadArticleBusy = this.articleService.getMyArticle(this.userInfo.id, this.page).subscribe(
            result => {
                this.articles = result.data.content;
                this.page = CommonPageModel.getPage(result.data);
                this.currentPage = this.page.number;
            }
        );
    }

    pageChanged(event: any) {
        this.page.number = event.page - 1;
        this.page.size = event.itemsPerPage;
        this.page.order = this.order;
        this.articleService.list(this.page).subscribe(
            result => {
                this.articles = result.data.content;
                this.page = CommonPageModel.getPage(result.data);
            }
        );
    }

}
