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
import {Component, OnInit, ViewChild} from '@angular/core';
import {Router} from '@angular/router';
import {Subscription} from 'rxjs/Subscription';
import {IOption} from 'ng-select';
import {ToastyService} from 'ng2-toasty';

import {ArticleModel} from '../../../../app/shared/model/article/article.model';
import {UserModel} from '../../../../app/shared/model/user/user.model';
import {CookieUtils} from '../../../shared/utils/cookie.util';
import {CommonConfig} from '../../../../config/common.config';
import {ArticleService} from '../../../../services/article.service';
import {ModalDirective} from 'ngx-bootstrap/modal';
import {ArticleTypeService} from '../../../../services/article.type.service';
import {ArticleTagService} from '../../../../services/article.tag.service';
import {ArticleTagModel} from '../../../shared/model/article/article.tag.model';
import {CodeConfig} from '../../../../config/code.config';
import {SpaceService} from '../../../../services/space.service';
import {CommonPageModel} from '../../../shared/model/result/page.model';
import {ResultUtils} from '../../../shared/utils/result.util';

@Component({
  selector: 'wikift-article-create',
  templateUrl: 'create.article.component.html'
})

export class CreateArticleComponent implements OnInit {

  public article: ArticleModel;
  // 文章类型
  public articleType;
  // 文章标签, 加载框
  public articleTags: Array<IOption>;
  public articleTagsBusy: Subscription;
  public articleTagFields;
  public articleTagValues = new Array();
  // 空间列表
  public spaces;
  // 分页数据
  public page: CommonPageModel;
  // 当前页数
  public currentPage: number;
  // 文章属性框
  @ViewChild('settingAritcleModel')
  public settingAritcleModel: ModalDirective;

  constructor(private router: Router,
              private articleService: ArticleService,
              private articleTypeService: ArticleTypeService,
              private articleTagService: ArticleTagService,
              private toastyService: ToastyService,
              private spaceService: SpaceService) {
    this.page = new CommonPageModel();
    this.page.size = 3;
    this.page.number = 0;
  }

  ngOnInit() {
    this.article = new ArticleModel();
    this.initArticelType();
  }

  initArticelType() {
    this.articleTypeService.list().subscribe(
      result => {
        this.articleType = result.data.content;
      }
    );
  }

  initArticleTag() {
    this.articleTagsBusy = this.articleTagService.list().subscribe(
      result => {
        if (result.data) {
          const tags = [];
          result.data.content.forEach(element => {
            const tag = {
              'value': element.id,
              'label': element.name
            };
            tags.push(tag);
          });
          this.articleTags = tags;
          console.log(this.articleTags);
        }
      }
    );
  }

  initSpace(page: CommonPageModel) {
    this.articleTagsBusy = this.spaceService.getAllSpacesByPublicOrUser(CookieUtils.getUser().id, page).subscribe(
      result => {
        this.spaces = result.data.content;
        this.page = CommonPageModel.getPage(result.data);
        this.currentPage = this.page.number;
      }
    );
  }

  // 获取编辑器内容
  getData(value) {
    this.article.content = value;
  }

  showSettingModel() {
    this.settingAritcleModel.show();
  }

  published() {
    const userModel = new UserModel();
    const user = JSON.parse(CookieUtils.getBy(CommonConfig.AUTH_USER_INFO));
    userModel.id = user.id;
    this.article.user = userModel;
    this.article.articleTags = new Array();
    this.articleTagFields.forEach(e => {
      const articleTag = new ArticleTagModel();
      articleTag.id = e;
      this.article.articleTags.push(articleTag);
    });
    this.articleService.save(this.article).subscribe(
      result => {
        if (result.code === CodeConfig.SUCCESS) {
          this.settingAritcleModel.hide();
          this.toastyService.info('创建文章' + this.article.title + '成功!!!');
          // 跳转到首页
          this.router.navigate(['/']);
        } else {
          this.toastyService.error(ResultUtils.getError(result));
        }
      }
    );
  }

  showArticleTagsStep(event) {
    this.initArticleTag();
  }

  showSpaceStep(event) {
    this.initSpace(this.page);
  }

  pageChanged(event: any) {
    this.page.number = event.page - 1;
    this.spaceService.getAllSpacesByPublicOrUser(CookieUtils.getUser().id, this.page).subscribe(
      result => {
        this.spaces = result.data.content;
        this.page = CommonPageModel.getPage(result.data);
      }
    );
  }

  onSelected(event: any) {
    this.articleTagValues.push(event.label);
  }

}
