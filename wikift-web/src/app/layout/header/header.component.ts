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
import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

import { CookieUtils } from '../../shared/utils/cookie.util';

import { SharedService } from '../../shared/services/shared.service';
import { UserService } from '../../../services/user.service';

import { UserModel } from '../../shared/model/user/user.model';
import { UserParamModel } from '../../shared/model/param/user.param.model';
import { CommonConfig } from '../../../config/common.config';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: [
    './header.component.scss'
  ]
})
export class HeaderComponent implements OnInit {

  token: String;
  userInfo: UserModel;
  maThemeModel = 'green';

  setTheme() {
    this.sharedService.setTheme(this.maThemeModel);
  }

  constructor(private sharedService: SharedService,
    private router: Router,
    private userService: UserService) {
    sharedService.maThemeSubject.subscribe((value) => {
      this.maThemeModel = value;
    });
  }

  ngOnInit() {
    this.token = CookieUtils.get();
    this.initUserInfo();
  }

  initUserInfo() {
    if (CookieUtils.getUserName() !== '') {
      const param = new UserParamModel();
      param.username = CookieUtils.getUserName();
      this.userService.info(param).subscribe(
        data => {
          this.userInfo = data.data;
          // 将用户信息存放到cookie中
          CookieUtils.setBy(CommonConfig.AUTH_USER_INFO, JSON.stringify(this.userInfo));
        }
      );
    }
  }

  logout() {
    CookieUtils.clear();
    this.router.navigate(['/login']);
  }

}
