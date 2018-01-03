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
import { Injectable } from '@angular/core';
import { Http, Headers, RequestOptions, URLSearchParams, QueryEncoder } from '@angular/http';
import { Router } from '@angular/router';
import { Observable } from 'rxjs/Observable';
import 'rxjs/add/operator/catch';
import 'rxjs/add/operator/map';
import { Cookie } from 'ng2-cookies';

import { HttpUtils } from '../app/shared/utils/http.util';
import { CookieUtils } from '../app/shared/utils/cookie.util';

import { ApiConfig } from '../config/api.config';
import { CommonConfig } from '../config/common.config';

import { LoginParamModel } from '../app/shared/model/param/login.param.model';
import { UserParamModel } from '../app/shared/model/param/user.param.model';
import { UserModel } from '../app/shared/model/user/user.model';
import { CommonResultModel } from '../app/shared/model/result/result.model';
import { ResultUtils } from '../app/shared/utils/result.util';
import { ToastyService } from 'ng2-toasty';

/**
 * 用户服务
 */
@Injectable()
export class UserService {

    constructor(
        private http: Http,
        private options: RequestOptions,
        private router: Router,
        private toastyService: ToastyService) {
    }

    /**
     * 登录
     */
    login(param: LoginParamModel) {
        console.log(`用户 ${param.username} 正在登录...`);
        Cookie.set(CommonConfig.AUTH_USER_NAME, param.username);
        const params = HttpUtils.getParams();
        params.append('username', param.username);
        params.append('password', param.password);
        params.append('grant_type', CommonConfig.AUTH_GRANT_TYPE);
        params.append('client_id', CommonConfig.AUTH_CLIENT_ID);
        const options = HttpUtils.getDefaultRequestOptionsByClient();
        options.params = params;
        return this.http.post(ApiConfig.AUTHORIZATION_API, param.toJson(), options)
            .map(res => res.json())
            .subscribe(
            data => {
                this.saveToken(data);
                return true;
            },
            err => {
                CookieUtils.clearBy(CommonConfig.AUTH_USER_NAME);
                this.toastyService.error('登录失败, 请检查您的用户名或密码');
                return false;
            });
    }

    /**
     * 保存授权的token
     * @param token token
     */
    saveToken(token) {
        const expireDate = new Date().getTime() + (1000 * token.expires_in);
        Cookie.set(CommonConfig.AUTH_TOKEN, token.access_token, expireDate);
        this.router.navigate(['/']);
    }

    /**
     * 检查是否有效
     */
    checkCredentials() {
        if (!CookieUtils.get()) {
            this.router.navigate(['/user/login']);
        }
    }

    /**
     * 获取用户信息
     * @param param 用户信息
     */
    info(param: UserParamModel): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByTokenAndJSON();
        const path = ApiConfig.API_USER_INFO + param.username;
        return this.http.get(path, options).map(ResultUtils.extractData);
    }

    /**
     * 注册用户
     * @param param 用户信息
     */
    register(param: UserParamModel): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptions();
        return this.http.post(ApiConfig.API_USER_REGISTER, JSON.stringify(param), options)
            .map(ResultUtils.extractData);
    }

    /**
     * 更新用户
     * @param param 用户信息
     */
    update(param: UserModel): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByTokenAndJSON();
        return this.http.put(ApiConfig.API_USER_UPDATE, JSON.stringify(param), options)
            .map(ResultUtils.extractData);
    }

    /**
     * 活跃用户排行榜
     */
    topByActive() {
        const options = HttpUtils.getDefaultRequestOptions();
        return this.http.get(ApiConfig.API_TOP_USER_BY_ACTIVE, options).map(ResultUtils.extractData);
    }

    /**
     * 关注用户
     * @param param 用户数据
     */
    follow(param: UserModel) {
        const options = HttpUtils.getDefaultRequestOptionsByTokenAndJSON();
        return this.http.put(ApiConfig.API_USER_FOLLOW, JSON.stringify(param), options)
            .map(ResultUtils.extractData);
    }

    /**
     * 取消关注用户
     * @param param 用户数据
     */
    unfollow(param: UserModel) {
        const options = HttpUtils.getDefaultRequestOptionsByTokenAndJSON();
        return this.http.put(ApiConfig.API_USER_UNFOLLOW, JSON.stringify(param), options)
            .map(ResultUtils.extractData);
    }

    /**
     * 关注的用户列表
     * @param userId 用户id
     */
    follows(userId) {
        const options = HttpUtils.getDefaultRequestOptionsByTokenAndJSON();
        return this.http.put(ApiConfig.API_USER_FOLLOWS + userId, options)
            .map(ResultUtils.extractData);
    }

    /**
     * 关注状态
     * @param followUserId 关注者
     * @param coverUserId 被关注者
     */
    followCheck(followUserId, coverUserId): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByToken();
        const params = HttpUtils.getParams();
        params.append('followUserId', followUserId);
        params.append('coverUserId', coverUserId);
        options.params = params;
        return this.http.get(ApiConfig.API_USER_FOLLOW_CHECK, options).map(ResultUtils.extractData);
    }

    /**
     * 关注/被关注用户统计
     * @param followUserId 用户id
     */
    followsCount(followUserId): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByToken();
        const params = HttpUtils.getParams();
        params.append('followUserId', followUserId);
        options.params = params;
        return this.http.get(ApiConfig.API_USER_FOLLOW_COUNT, options).map(ResultUtils.extractData);
    }

}
