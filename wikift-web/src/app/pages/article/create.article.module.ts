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
import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { Routes, RouterModule } from '@angular/router';

import { CreateArticleComponent } from './create.article.component';
import { WikiftEditorComponent } from '../../shared/directives/wikift-editor/wikift-editor.component';

import { ArticleService } from '../../../services/article.service';

const CREATE_ARTICLE_ROUTES: Routes = [
    { path: '', component: CreateArticleComponent }
];

@NgModule({
    imports: [
        CommonModule,
        RouterModule.forChild(CREATE_ARTICLE_ROUTES)
    ],
    exports: [],
    declarations: [
        CreateArticleComponent,
        WikiftEditorComponent
    ],
    providers: [
        ArticleService
    ],
})
export class CreateArticleModule { }
