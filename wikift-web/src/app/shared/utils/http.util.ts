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
import { Headers, RequestOptions, URLSearchParams, QueryEncoder } from '@angular/http';

import { CommonConfig } from '../../../config/common.config';

import { CookieUtils } from '../utils/cookie.util';

/**
 * 全局HTTP请求
 *
 * @author shicheng
 */
export class HttpUtils {

    /**
     * 默认参数header使用JSON数据传输方式
     */
    public static getDefaultRequestOptions() {
        const headers = new Headers({
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        });
        const options = new RequestOptions({ headers: headers });
        return options;
    }

    public static getDefaultRequestOptionsByClient() {
        const headers = new Headers({
            'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
            'Authorization': 'Basic ' + btoa(CommonConfig.AUTH_CLIENT_ID + ':' + CommonConfig.AUTH_CLIENT_SECRET)
        });
        const options = new RequestOptions({ headers: headers });
        return options;
    }

    public static getDefaultRequestOptionsByToken() {
        const headers = new Headers({
            'Content-Type': 'application/x-www-form-urlencoded; charset=utf-8',
            'Authorization': 'Bearer ' + CookieUtils.get()
        });
        const options = new RequestOptions({ headers: headers });
        return options;
    }

    /**
     * 分页查询参数
     * @param page 页数
     * @param size 页面总数
     */
    public static getPaginationParams(page: number, size: number): URLSearchParams {
        const params = new URLSearchParams();
        params.append('page', page.toString());
        params.append('size', size.toString());
        return params;
    }

    /**
     * 查询参数
     */
    public static getParams(): URLSearchParams {
        const params = new URLSearchParams();
        return params;
    }

}

class UrlQueryEncoder extends QueryEncoder {
    encodeKey(k: string): string {
        return super.encodeKey(k).replace(/\+/gi, '%2B');
    }
    encodeValue(v: string): string {
        return super.encodeValue(v).replace(/\+/gi, '%2B');
    }
}

export const URL_QUERY_ENCODER = new UrlQueryEncoder();
