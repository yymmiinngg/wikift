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

import { ArticleModel } from '../app/shared/model/article/article.model';
import { CommonResultModel } from '../app/shared/model/result/result.model';
import { CommonPageModel } from '../app/shared/model/result/page.model';
import { ArticleFabulousParamModel } from '../app/shared/model/param/article.fabulous.param.model';


/**
 * 文章服务
 */
@Injectable()
export class ArticleService {

    constructor(
        private http: Http,
        private options: RequestOptions) {
    }

    save(param: ArticleModel): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByTokenAndJSON();
        return this.http.post(ApiConfig.API_ARTICLE_SAVE, JSON.stringify(param), options)
            .map(ResultUtils.extractData);
    }

    update(param: ArticleModel): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByTokenAndJSON();
        return this.http.put(ApiConfig.API_ARTICLE_UPDATE, JSON.stringify(param), options)
            .map(ResultUtils.extractData);
    }

    delete(param: ArticleModel): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByTokenAndJSON();
        return this.http.delete(ApiConfig.API_ARTICLE_DELETE + param.id, options)
            .map(ResultUtils.extractData);
    }

    list(page: CommonPageModel): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptions();
        const params = HttpUtils.getParams();
        params.append('page', page.number.toString());
        params.append('size', page.size.toString());
        options.params = params;
        return this.http.get(ApiConfig.API_ARTICLE_LIST, options).map(ResultUtils.extractData);
    }

    info(param: ArticleModel): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptions();
        return this.http.get(ApiConfig.API_ARTICLE_INFO + param.id, options).map(ResultUtils.extractData);
    }

    findTopByUser(userId): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByToken();
        const params = HttpUtils.getParams();
        params.append('username', userId);
        options.params = params;
        return this.http.get(ApiConfig.API_ARTICLE_TOP_BY_USER, options).map(ResultUtils.extractData);
    }

    fabulous(param: ArticleFabulousParamModel): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByTokenAndJSON();
        return this.http.post(ApiConfig.API_ARTICLE_FABULOUS, param.toJosn(), options).map(ResultUtils.extractData);
    }

    fabulousCheck(param: ArticleFabulousParamModel): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByToken();
        const params = HttpUtils.getParams();
        params.append('userId', param.userId);
        params.append('articleId', param.articleId);
        options.params = params;
        return this.http.get(ApiConfig.API_ARTICLE_FABULOUS_CHECK, options).map(ResultUtils.extractData);
    }

    unfabulous(param: ArticleFabulousParamModel): Observable<CommonResultModel> {
        const options = HttpUtils.getDefaultRequestOptionsByToken();
        const path = ApiConfig.API_ARTICLE_UNFABULOUS + param.userId + '/' + param.articleId;
        return this.http.delete(path, options).map(ResultUtils.extractData);
    }

}
