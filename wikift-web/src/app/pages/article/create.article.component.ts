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

import { ArticleModel } from '../../../app/shared/model/article/article.model';
import { UserModel } from '../../../app/shared/model/user/user.model';
import { CookieUtils } from '../../shared/utils/cookie.util';
import { CommonConfig } from '../../../config/common.config';
import { ArticleService } from '../../../services/article.service';

@Component({
    selector: 'wikift-article-create',
    templateUrl: 'create.article.component.html'
})

export class CreateArticleComponent implements OnInit {

    dataText;

    constructor(private articleService: ArticleService) { }

    ngOnInit() {
    }

    getData(value) {
        this.dataText = value;
    }

    published() {
        const articleModel = new ArticleModel();
        articleModel.title = new Date().getMilliseconds().toString();
        articleModel.content = this.dataText;
        const userModel = new UserModel();
        const user = JSON.parse(CookieUtils.getBy(CommonConfig.AUTH_USER_INFO));
        userModel.id = user.id;
        articleModel.userEntity = userModel;
        this.articleService.save(articleModel).subscribe(
            result => {
                console.log(result.data);
            }
        );
    }

}
