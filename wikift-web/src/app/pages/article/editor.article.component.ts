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

import { ArticleModel } from '../../../app/shared/model/article/article.model';
import { UserModel } from '../../../app/shared/model/user/user.model';
import { CookieUtils } from '../../shared/utils/cookie.util';
import { CommonConfig } from '../../../config/common.config';
import { ArticleService } from '../../../services/article.service';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ArticleTypeService } from '../../../services/article.type.service';

@Component({
    selector: 'wikift-article-editor',
    templateUrl: 'editor.article.component.html'
})

export class EditorArticleComponent implements OnInit {

    // 文章id
    id: number;
    article: ArticleModel;
    // 文章类型
    public articleType;
    @ViewChild('settingAritcleModel')
    public settingAritcleModel: ModalDirective;

    constructor(private route: ActivatedRoute,
        private router: Router,
        private articleService: ArticleService,
        private articleTypeService: ArticleTypeService) {
        this.route.params.subscribe((params) => this.id = params.id);
    }

    showSettingModel() {
        this.settingAritcleModel.show();
    }

    // 获取编辑器内容
    getData(value) {
        this.article.content = value;
    }

    ngOnInit() {
        this.article = new ArticleModel();
        const params = new ArticleModel();
        params.id = this.id;
        this.articleService.info(params).subscribe(
            result => { this.article = result.data; }
        );
        this.initArticelType();
    }

    initArticelType() {
        this.articleTypeService.list().subscribe(
            result => {
                this.articleType = result.data;
            }
        );
    }

    updated() {
        this.articleService.update(this.article).subscribe(
            result => {
                this.settingAritcleModel.hide();
                this.router.navigate(['/']);
            }
        );
    }

}
