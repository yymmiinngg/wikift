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
import { Router, ActivatedRoute } from '@angular/router';

import { UserService } from '../../../../services/user.service';
import { WikiftGroupConfig } from '../../../shared/directives/wikift-group/model/wikift-group.model';
import { WikiftTypeModel } from '../../../shared/directives/wikift-group/model/wikift-group.enum.model';

@Component({
    selector: 'wikift-user-followed',
    templateUrl: 'user.followed.component.html'
})
export class UserInfoComponent implements OnInit {

    public followers;
    // 用户保存传递的用户名称
    public username;
    public config: WikiftGroupConfig;

    constructor(private route: ActivatedRoute,
        private userService: UserService) { }

    ngOnInit() {
        this.route.params.subscribe((params) => this.username = params.username);
        this.initFollowing();
        this.config = new WikiftGroupConfig();
        this.config.type = WikiftTypeModel.USER;
    }

    initFollowing() {
        this.userService.getUserFollwing(this.username).subscribe(
            result => {
                this.followers = result.data;
            }
        );
    }

    // unfollow() {
    //     const follows = new Array();
    //     follows.push(this.user);
    //     this.currentUser.follows = follows;
    //     this.userService.unfollow(this.currentUser).subscribe(
    //         result => {
    //             if (result.data) {
    //                 this.isFollow = true;
    //             }
    //         }
    //     );
    // }

}
