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
import { Routes, RouterModule } from '@angular/router';
import { LayoutComponent } from './layout.component';

import { AuthGuard } from '../../app/auth/auth.guard';

import { LoginComponent } from '../pages/user/login/login.component';

const LAYOUT_ROUTES: Routes = [
    {
        path: '', component: LayoutComponent, children: [
            { path: '', redirectTo: 'home', pathMatch: 'full' },
            { path: 'home', loadChildren: '../pages/home/home.module#HomeModule' },
            { path: 'article/info/:id', loadChildren: '../pages/article/info.article.module#InfoArticleModule' },
        ]
    },
    {
        path: '', component: LayoutComponent, canActivate: [AuthGuard], children: [
            { path: 'article/create', loadChildren: '../pages/article/create.article.module#CreateArticleModule' },
            { path: 'article/:id/editor', loadChildren: '../pages/article/editor.article.module#EditorArticleModule' },
            { path: 'account/:username/info', loadChildren: '../pages/user/info/user.info.module#UserInfoModule' },
        ]
    },
    {
        path: 'user', children: [
            { path: 'login', component: LoginComponent },
            { path: 'register', loadChildren: '../pages/user/register/user.register.module#UserRegisterModule' },
        ]
    }
];

export const LayoutRouting = RouterModule.forChild(LAYOUT_ROUTES);
