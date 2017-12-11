import { Injectable } from '@angular/core';
import { Router, CanActivate, ActivatedRouteSnapshot, RouterStateSnapshot } from '@angular/router';

import { CommonConfig } from '../../config/common.config';

import { Cookie } from 'ng2-cookies';

/**
 * 用户授权
 */
@Injectable()
export class AuthGuard implements CanActivate {

    constructor(private router: Router) { }

    canActivate(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        // 存储appToken到session
        if (!Cookie.get(CommonConfig.AUTH_TOKEN)) {
            this.router.navigate(['/login']);
            return false;
        } else {
            return true;
        }
    }

}
