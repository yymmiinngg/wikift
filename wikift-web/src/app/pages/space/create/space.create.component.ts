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
import { Router } from '@angular/router';
import { ImageCropperComponent, CropperSettings } from 'ng2-img-cropper';

import { SpaceModel } from '../../../shared/model/space/space.model';
import { CookieUtils } from '../../../shared/utils/cookie.util';
import { CommonConfig } from '../../../../config/common.config';
import { UserModel } from '../../../shared/model/user/user.model';
import { SpaceService } from '../../../../services/space.service';
import { CodeConfig } from '../../../../config/code.config';

@Component({
    selector: 'wikift-space-create',
    templateUrl: 'space.create.component.html'
})

export class SpaceCreateComponent implements OnInit {

    // 用户提交空间表单信息
    public spaceModel: SpaceModel;
    // cropper 配置
    public cropperSettings: CropperSettings;
    // cropper 图像
    public cropperImage; any;

    constructor(private router: Router,
        private spaceService: SpaceService) {
        this.spaceModel = new SpaceModel();
        this.cropperSettings = new CropperSettings();
        this.cropperSettings.keepAspect = false;
        // 截取图片的大小
        this.cropperSettings.croppedWidth = 200;
        this.cropperSettings.croppedHeight = 200;
        this.cropperSettings.minWidth = 200;
        this.cropperSettings.minHeight = 200;
        this.cropperSettings.rounded = true;
        this.cropperSettings.minWithRelativeToResolution = false;
        this.cropperSettings.cropperDrawSettings.strokeColor = 'rgba(1,125,125,1)';
        this.cropperSettings.cropperDrawSettings.strokeWidth = 1;
        this.cropperImage = {};
    }

    ngOnInit() {
    }

    cropped(event) {
        this.spaceModel.avatar = this.cropperImage.image;
    }

    createSpace() {
        const user: UserModel = JSON.parse(CookieUtils.getBy(CommonConfig.AUTH_USER_INFO));
        this.spaceModel.user = user;
        this.spaceService.createSpace(this.spaceModel).subscribe(
            result => {
                if (result.data === CodeConfig.SUCCESS) {
                    this.router.navigate(['/space']);
                }
            }
        );
    }

}
