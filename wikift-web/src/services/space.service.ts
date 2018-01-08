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

import { HttpUtils } from '../app/shared/utils/http.util';
import { CookieUtils } from '../app/shared/utils/cookie.util';
import { ResultUtils } from '../app/shared/utils/result.util';

import { ApiConfig } from '../config/api.config';

import { CommonResultModel } from '../app/shared/model/result/result.model';
import { SpaceModel } from '../app/shared/model/space/space.model';
import { CommonPageModel } from '../app/shared/model/result/page.model';

/**
 * 空间服务
 * @author qianmoQ
 */
@Injectable()
export class SpaceService {

    constructor(
        private http: Http,
        private options: RequestOptions) {
    }

    /**
     * 获取所有的空间
     */
    getAllSpaces(page: CommonPageModel): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByToken();
        const params = HttpUtils.getPaginationParamsByModel(page);
        options.params = params;
        return this.http.get(ApiConfig.API_SPACE_LIST, options).map(ResultUtils.extractData);
    }

    getAllSpacesByUser(userId: number): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByToken();
        const path = ApiConfig.API_SPACE_LIST_USER + '/' + userId;
        return this.http.get(path, options).map(ResultUtils.extractData);
    }

    getAllSpacesByPublicOrUser(userId: number, page: CommonPageModel): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByToken();
        const path = ApiConfig.API_SPACE_LIST_PUBLIC_USER + '/' + userId;
        const params = HttpUtils.getPaginationParamsByModel(page);
        options.params = params;
        return this.http.get(path, options).map(ResultUtils.extractData);
    }

    /**
     * 创建空间
     */
    createSpace(param: SpaceModel): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByTokenAndJSON();
        return this.http.post(ApiConfig.API_SPACE_CREATE, JSON.stringify(param), options)
            .map(ResultUtils.extractData);
    }

    getAllArticleBySpace(page: CommonPageModel, spaceCode: string): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByToken();
        const params = HttpUtils.getPaginationParamsByModel(page);
        params.append('spaceCode', spaceCode);
        options.params = params;
        return this.http.get(ApiConfig.API_SPACE_ARTICLE, options).map(ResultUtils.extractData);
    }

}
