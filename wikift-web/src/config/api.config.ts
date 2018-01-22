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
export class ApiConfig {

    public static AUTHORIZATION_API = '/oauth/token';

    private static V1_API_ROOT = '/api/v1/';
    private static V1_API_ROOT_PUBLIC = ApiConfig.V1_API_ROOT + 'public';

    public static API_USER_INFO = ApiConfig.V1_API_ROOT + 'user/info/';
    public static API_USER_UPDATE = ApiConfig.V1_API_ROOT + 'user/update';
    public static API_USER_UPDATE_EMAIL = ApiConfig.V1_API_ROOT + 'user/update/email';
    public static API_USER_UPDATE_PASSWORD = ApiConfig.V1_API_ROOT + 'user/update/password';
    public static API_TOP_USER_BY_ACTIVE = ApiConfig.V1_API_ROOT_PUBLIC + '/user/top';
    public static API_USER_FOLLOW = ApiConfig.V1_API_ROOT + 'user/follow';
    public static API_USER_REGISTER = ApiConfig.V1_API_ROOT + 'user/register';
    public static API_USER_UNFOLLOW = ApiConfig.V1_API_ROOT + 'user/unfollow';
    public static API_USER_FOLLOWS = ApiConfig.V1_API_ROOT + 'user/follows/';
    public static API_USER_FOLLOW_CHECK = ApiConfig.V1_API_ROOT + 'user/follows/check';
    public static API_USER_FOLLOW_COUNT = ApiConfig.V1_API_ROOT + 'user/follows/count';

    public static API_ARTICLE_LIST = ApiConfig.V1_API_ROOT_PUBLIC + '/article/list';
    public static API_ARTICLE_SAVE = ApiConfig.V1_API_ROOT + 'article/create';
    public static API_ARTICLE_INFO = ApiConfig.V1_API_ROOT_PUBLIC + '/article/info/';
    public static API_ARTICLE_UPDATE = ApiConfig.V1_API_ROOT + 'article/update';
    public static API_ARTICLE_DELETE = ApiConfig.V1_API_ROOT + 'article/delete/';
    public static API_ARTICLE_TOP_BY_USER = ApiConfig.V1_API_ROOT + 'article/top/by/user';
    public static API_ARTICLE_FABULOUS = ApiConfig.V1_API_ROOT + 'article/fabulous';
    public static API_ARTICLE_FABULOUS_CHECK = ApiConfig.V1_API_ROOT + 'article/fabulous/check';
    public static API_ARTICLE_FABULOUS_COUNT = ApiConfig.V1_API_ROOT + 'article/fabulous/count';
    public static API_ARTICLE_UNFABULOUS = ApiConfig.V1_API_ROOT + 'article/unfabulous/';
    public static API_ARTICLE_VIEW = ApiConfig.V1_API_ROOT_PUBLIC + '/article/view';
    public static API_ARTICLE_VIEW_COUNT = ApiConfig.V1_API_ROOT + 'article/view/count';
    public static API_ARTICLE_VIEW_TOP_WEEK = ApiConfig.V1_API_ROOT_PUBLIC + '/article/view/';
    public static API_ARTICLE_FOR_MY = ApiConfig.V1_API_ROOT + 'article/my';

    public static API_ARTICLE_TYPE_LIST = ApiConfig.V1_API_ROOT + 'article/type/list';

    public static API_ARTICLE_TAG_LIST = ApiConfig.V1_API_ROOT + 'article/tag/list';
    public static API_ARTICLE_TAG_TOP = ApiConfig.V1_API_ROOT_PUBLIC + '/article/tag/top';

    public static API_REMIND_LIST_BY_USER = ApiConfig.V1_API_ROOT + 'remind/list/user';
    public static API_REMIND_INFO = ApiConfig.V1_API_ROOT + 'remind/info/';
    public static API_REMIND_READ = ApiConfig.V1_API_ROOT + 'remind/read/';

    public static API_SPACE_LIST = ApiConfig.V1_API_ROOT + 'space/list';
    public static API_SPACE_CREATE = ApiConfig.V1_API_ROOT + 'space/create';
    public static API_SPACE_LIST_PUBLIC_USER = ApiConfig.V1_API_ROOT + 'space/list/public/user';
    public static API_SPACE_LIST_USER = ApiConfig.V1_API_ROOT + 'space/list/user';
    public static API_SPACE_ARTICLE = ApiConfig.V1_API_ROOT + 'space/article';
    public static API_SPACE_INFO_CODE = ApiConfig.V1_API_ROOT + 'space/info/code/';
    public static API_SPACE_INFO = ApiConfig.V1_API_ROOT + 'space/info/';

    public static API_COMMENT_CREATE = ApiConfig.V1_API_ROOT + 'comment/create';
    public static API_COMMENT_LIST = ApiConfig.V1_API_ROOT_PUBLIC + '/comment/list';
    public static API_COMMENT_COUNTER_TOP_WEEK = ApiConfig.V1_API_ROOT + 'comment/view/';
    public static API_COMMENT_DELETE = ApiConfig.V1_API_ROOT + 'comment/delete/';

}
