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
import { Router, ActivatedRoute } from '@angular/router';

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
import { CodeConfig } from '../../../config/code.config';

@Component({
    selector: 'wikift-search',
    templateUrl: 'search.component.html'
})

export class SearchComponent implements OnInit {

    public searchData;
    // 分页数据
    public page: CommonPageModel;
    // 当前页数
    public currentPage: number;
    public loadArticleBusy: Subscription;
    // 抽取layout布局的用户信息
    public userInfo;
    // 搜索的关键词
    public searchValue;

    constructor(private route: ActivatedRoute,
        private articleService: ArticleService,
        private userService: UserService,
        private articleTagService: ArticleTagService) {
        this.page = new CommonPageModel();
        this.userInfo = CookieUtils.getUser();
    }

    ngOnInit() {
        this.route.queryParams.subscribe(params => {
            const tag = params['tag'];
            if (tag) {
                this.searchValue = tag;
            }
        });
        this.initArticleList(this.page, this.searchValue);
    }

    searchContent() {
        if (this.searchValue) {
            this.initArticleList(this.page, this.searchValue);
        } else {
            alert('搜索的关键词不能为空!!!');
        }
    }

    /**
     * 文章列表
     */
    initArticleList(page: CommonPageModel, search: string) {
        page.number = 0;
        page.size = 10;
        this.loadArticleBusy = this.articleService.filterByTag(page, search).subscribe(
            result => {
                this.searchData = result.data;
                if (result.code === CodeConfig.SUCCESS) {
                    this.page = CommonPageModel.getPage(result.data);
                    this.currentPage = this.page.number;
                }
            }
        );
    }

    pageChanged(event: any) {
        this.page.number = event.page - 1;
        this.page.size = event.itemsPerPage;
        this.articleService.list(this.page).subscribe(
            result => {
                this.searchValue = result.data;
                this.page = CommonPageModel.getPage(result.data);
            }
        );
    }

}
