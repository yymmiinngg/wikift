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

import { ArticleModel } from '../../../app/shared/model/article/article.model';
import { UserModel } from '../../../app/shared/model/user/user.model';
import { CookieUtils } from '../../shared/utils/cookie.util';
import { CommonConfig } from '../../../config/common.config';
import { ArticleService } from '../../../services/article.service';
import { ModalDirective } from '_ngx-bootstrap@2.0.0-beta.10@ngx-bootstrap/modal/modal.directive';

@Component({
    selector: 'wikift-article-create',
    templateUrl: 'create.article.component.html'
})

export class CreateArticleComponent implements OnInit {

    articleModel: ArticleModel;

    // 文章属性框
    @ViewChild('settingAritcleModel')
    public settingAritcleModel: ModalDirective;

    constructor(private router: Router,
        private articleService: ArticleService) { }

    ngOnInit() {
        this.articleModel = new ArticleModel();
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
        this.articleService.save(this.articleModel).subscribe(
            result => {
                this.settingAritcleModel.hide();
                // 跳转到首页
                this.router.navigate(['/']);
            }
        );
    }

}
