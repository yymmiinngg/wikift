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
import { Component, OnInit, Input } from '@angular/core';
import { AfterViewInit, OnChanges, ViewChild } from '@angular/core';
import { WikiftGroupConfig } from './model/wikift-group.model';
import { WikiftTypeModel } from './model/wikift-group.enum.model';
import { CookieUtils } from '../../utils/cookie.util';
import { CommonConfig } from '../../../../config/common.config';
import { UserService } from '../../../../services/user.service';

declare var jQuery: any;
declare var editormd: any;

@Component({
    selector: 'wikift-group',
    templateUrl: './wikift-group.component.html'
})
export class WikiftGroupComponent implements OnInit {

    // store a reference to WikiftTypeModel enum
    type = WikiftTypeModel;

    // wikift group component each data
    @Input()
    eachData: Array<object>;

    // wikift group component config
    @Input()
    config: WikiftGroupConfig;

    constructor(private userService: UserService) {
    }

    ngOnInit(): void {
        // use default config
        if (!this.config) {
            this.config = new WikiftGroupConfig();
        }
    }

    unfollow(following) {
        const follows = new Array();
        follows.push(following);
        let currentUser;
        if (CookieUtils.getBy(CommonConfig.AUTH_USER_INFO)) {
            currentUser = JSON.parse(CookieUtils.getBy(CommonConfig.AUTH_USER_INFO));
        }
        currentUser.follows = follows;
        this.userService.unfollow(currentUser).subscribe(
            result => {
                if (result.data) {
                    window.location.reload();
                }
            }
        );
    }

}
