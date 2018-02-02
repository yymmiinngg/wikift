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
import { IOption } from 'ng-select';

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
import { SpaceService } from '../../../services/space.service';

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
    // 显示高级搜索
    public showSeniorSettings = false;
    // 文章标签
    public articleTags;
    // 文章空间
    public articleSpaces;
    // 文章用户
    public articleUsers;
    // 搜索的文章名称
    public searchArticleTitle;
    // 搜索的文章标签
    public searchArticleTag;
    // 搜索的文章空间
    public searchArticleSpace;
    // 搜索的文章用户
    public searchArticleUser;

    constructor(private route: ActivatedRoute,
        private articleService: ArticleService,
        private userService: UserService,
        private articleTagService: ArticleTagService,
        private spaceService: SpaceService) {
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

    seniorSettings() {
        if (this.showSeniorSettings) {
            this.showSeniorSettings = false;
        } else {
            this.showSeniorSettings = true;
            this.initArticleTag();
            this.initSpace();
            this.initUser();
        }
    }

    generateOptions(any) {
        const options = [];
        any.forEach(element => {
            let title = element.title;
            if (!title) {
                title = element.name;
            }
            if (!title) {
                title = element.username;
            }
            const option = {
                'value': element.id,
                'label': title
            };
            options.push(option);
        });
        return options;
    }

    initArticleTag() {
        this.articleTagService.list().subscribe(
            result => {
                if (result.data) {
                    this.articleTags = this.generateOptions(result.data.content);
                }
            }
        );
    }

    initSpace() {
        this.spaceService.getAllPublicSpaces().subscribe(
            result => {
                if (result.data) {
                    this.articleSpaces = this.generateOptions(result.data.content);
                }
            }
        );
    }

    initUser() {
        this.userService.list().subscribe(
            result => {
                if (result.data) {
                    this.articleUsers = this.generateOptions(result.data);
                }
            }
        );
    }

    onSearchArticleTitle() {
        this.globalSearch(this.searchArticleTag, this.searchArticleTitle, this.searchArticleSpace, this.searchArticleUser);
    }

    onSearchArticleTagSelected(option: IOption) {
        this.searchArticleTag = option.value;
        this.globalSearch(this.searchArticleTag, this.searchArticleTitle, this.searchArticleSpace, this.searchArticleUser);
    }

    onSearchArticleSpaceSelected(option: IOption) {
        this.searchArticleSpace = option.value;
        this.globalSearch(this.searchArticleTag, this.searchArticleTitle, this.searchArticleSpace, this.searchArticleUser);
    }

    onSearchArticleUserSelected(option: IOption) {
        console.log(option);
        this.searchArticleUser = option.value;
        this.globalSearch(this.searchArticleTag, this.searchArticleTitle, this.searchArticleSpace, this.searchArticleUser);
    }


    globalSearch(articleTag: number, articleTitle: string, articleSpace: number, articleUser: number) {
        this.loadArticleBusy = this.articleService.search(this.page, articleTag, articleTitle, articleSpace, articleUser).subscribe(
            result => {
                this.searchData = result.data;
                if (result.code === CodeConfig.SUCCESS) {
                    this.page = CommonPageModel.getPage(result.data);
                    this.currentPage = this.page.number;
                }
            }
        );
    }

}
