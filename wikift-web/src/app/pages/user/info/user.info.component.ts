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
import { CookieUtils } from '../../../shared/utils/cookie.util';
import { UserParamModel } from '../../../shared/model/param/user.param.model';
import { CommonConfig } from '../../../../config/common.config';
import { UserModel } from '../../../shared/model/user/user.model';
import { UserService } from '../../../../services/user.service';
import { Router, ActivatedRoute } from '@angular/router';
import { ModalDirective } from 'ngx-bootstrap/modal';
import { ArticleService } from '../../../../services/article.service';

@Component({
    selector: 'wikift-user-info',
    templateUrl: 'user.info.component.html'
})

export class UserInfoComponent implements OnInit {

    // 当前登录的用户
    currentUser;
    // 当前用户
    user;
    // 更新的用户信息
    commitUser: UserModel = new UserModel();
    // 当前用户名称
    username: string;
    @ViewChild('settingsUserProfile')
    public settingsUserProfile: ModalDirective;
    // 用户一周最新文章
    userTopArticles;

    constructor(private route: ActivatedRoute,
        private userService: UserService,
        private articleService: ArticleService) { }

    ngOnInit() {
        if (CookieUtils.getBy(CommonConfig.AUTH_USER_INFO)) {
            this.currentUser = JSON.parse(CookieUtils.getBy(CommonConfig.AUTH_USER_INFO));
        }
        this.route.params.subscribe((params) => this.username = params.username);
        this.initUserInfo();
        this.initTopByUser();
    }

    initUserInfo() {
        const param = new UserParamModel();
        param.username = this.username;
        this.userService.info(param).subscribe(
            result => {
                this.user = result.data;
                Object.assign(this.commitUser, this.user);
            }
        );
    }

    initTopByUser() {
        this.articleService.findTopByUser(this.username).subscribe(
            result => {
                this.userTopArticles = result.data;
            }
        );
    }

    openSettingsUserProfile() {
        this.settingsUserProfile.show();
    }

    updated() {
        this.userService.update(this.commitUser).subscribe(
            result => {
                this.settingsUserProfile.hide();
                // 刷新页面数据
                this.initUserInfo();
            }
        );
    }

}
