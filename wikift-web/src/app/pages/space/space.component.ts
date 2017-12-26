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
import { Subscription } from 'rxjs/Subscription';

import { ArticleModel } from '../../shared/model/article/article.model';
import { UserModel } from '../../shared/model/user/user.model';
import { CommonResultModel } from '../../shared/model/result/result.model';

import { CookieUtils } from '../../shared/utils/cookie.util';

import { CommonConfig } from '../../../config/common.config';

import { ArticleService } from '../../../services/article.service';
import { UserService } from '../../../services/user.service';
import { CommonPageModel } from '../../shared/model/result/page.model';
import { OrderEnumModel } from '../../shared/model/enum/order.enum.model';
import { SpaceService } from '../../../services/space.service';

@Component({
    selector: 'wikift-space',
    templateUrl: 'space.component.html'
})

export class SpaceComponent implements OnInit {

    // 分页数据
    page: CommonPageModel;
    // 空间列表
    public spaces;

    constructor(private articleService: ArticleService,
        private userService: UserService,
        private spaceService: SpaceService) {
        this.page = new CommonPageModel();
    }

    ngOnInit() {
        this.initSpaceList();
    }

    initSpaceList() {
        this.spaceService.getAllSpaces().subscribe(
            result => {
                this.spaces = result.data;
            }
        );
    }

}
