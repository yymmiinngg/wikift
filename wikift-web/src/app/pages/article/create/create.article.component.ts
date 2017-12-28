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
import { Router } from '@angular/router';
import { Subscription } from 'rxjs/Subscription';
import { Select2OptionData, Select2Component } from 'ng2-select2';
import { ToastyService } from 'ng2-toasty';

import { ArticleModel } from '../../../../app/shared/model/article/article.model';
import { UserModel } from '../../../../app/shared/model/user/user.model';
import { CookieUtils } from '../../../shared/utils/cookie.util';
import { CommonConfig } from '../../../../config/common.config';
import { ArticleService } from '../../../../services/article.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ArticleTypeService } from '../../../../services/article.type.service';
import { ArticleTagService } from '../../../../services/article.tag.service';
import { ArticleTagModel } from '../../../shared/model/article/article.tag.model';
import { CodeConfig } from '../../../../config/code.config';
import { SpaceService } from '../../../../services/space.service';

@Component({
    selector: 'wikift-article-create',
    templateUrl: 'create.article.component.html'
})

export class CreateArticleComponent implements OnInit {

    // 选择标签配置
    multipleOptions: any = {
        multiple: true,
        dropdownAutoWidth: true,
        placeholder: '请选择文章标签',
        width: '100%',
        containerCssClass: 'select2-selection--alt',
        dropdownCssClass: 'select2-dropdown--alt'
    };
    articleModel: ArticleModel;
    // 文章类型
    public articleType;
    // 文章标签, 加载框
    public articleTags: Array<Select2OptionData>;
    public articleTagsBusy: Subscription;
    @ViewChild('articleTagFields')
    public articleTagFields: Select2Component;
    public tagsValue: any = [];
    public articleTagsValue: any = [];
    // 空间列表
    private spaces;

    // 文章属性框
    @ViewChild('settingAritcleModel')
    public settingAritcleModel: ModalDirective;

    constructor(private router: Router,
        private articleService: ArticleService,
        private articleTypeService: ArticleTypeService,
        private articleTagService: ArticleTagService,
        private toastyService: ToastyService,
        private spaceService: SpaceService) { }

    ngOnInit() {
        this.articleModel = new ArticleModel();
        this.initArticelType();
    }

    initArticelType() {
        this.articleTypeService.list().subscribe(
            result => {
                this.articleType = result.data;
            }
        );
    }

    initArticleTag() {
        this.articleTagsBusy = this.articleTagService.list().subscribe(
            result => {
                if (result.data) {
                    const tags = [];
                    result.data.content.forEach(element => {
                        const tag = {
                            'id': element.id,
                            'text': element.title
                        };
                        tags.push(tag);
                    });
                    this.articleTags = tags;
                }
            }
        );
    }

    initSpace() {
        this.articleTagsBusy = this.spaceService.getAllSpacesByPublicOrUser(CookieUtils.getUser().id).subscribe(
            result => {
                this.spaces = result.data;
            }
        );
    }

    // 获取编辑器内容
    getData(value) {
        this.articleModel.content = value;
    }

    showSettingModel() {
        this.settingAritcleModel.show();
    }

    published() {
        const userModel = new UserModel();
        const user = JSON.parse(CookieUtils.getBy(CommonConfig.AUTH_USER_INFO));
        userModel.id = user.id;
        this.articleModel.userEntity = userModel;
        this.articleModel.articleTags = new Array();
        this.articleTagsValue.value.forEach(e => {
            const articleTag = new ArticleTagModel();
            articleTag.id = e;
            this.articleModel.articleTags.push(articleTag);
        });
        console.log(this.articleModel);
        this.articleService.save(this.articleModel).subscribe(
            result => {
                if (result.code === CodeConfig.SUCCESS) {
                    this.settingAritcleModel.hide();
                    this.toastyService.info('创建文章' + this.articleModel.title + '成功!!!');
                    // 跳转到首页
                    this.router.navigate(['/']);
                } else {
                    this.toastyService.info('参数填写错误');
                }
            }
        );
    }

    showArticleTagsStep(event) {
        this.initArticleTag();
    }

    showSpaceStep(event) {
        this.initSpace();
    }

    tagChanged(data: { value: string[] }) {
        this.articleTagsValue = data;
    }

}
