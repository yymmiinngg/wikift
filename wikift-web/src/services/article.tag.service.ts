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
import { CommonPageModel } from '../app/shared/model/result/page.model';

/**
 * 文章标签服务
 */
@Injectable()
export class ArticleTagService {

    constructor(
        private http: Http,
        private options: RequestOptions) {
    }

    list(): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptions();
        return this.http.get(ApiConfig.API_ARTICLE_TAG_LIST, options).map(ResultUtils.extractData);
    }

    top(top?: number): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptions();
        if (top) {
            const params = HttpUtils.getParams();
            params.append('top', top.toString());
            options.params = params;
        }
        return this.http.get(ApiConfig.API_ARTICLE_TAG_TOP, options).map(ResultUtils.extractData);
    }

}
