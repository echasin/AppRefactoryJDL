import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AssetstatusComponent } from './assetstatus.component';
import { AssetstatusDetailComponent } from './assetstatus-detail.component';
import { AssetstatusPopupComponent } from './assetstatus-dialog.component';
import { AssetstatusDeletePopupComponent } from './assetstatus-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class AssetstatusResolvePagingParams implements Resolve<any> {

    constructor(private paginationUtil: PaginationUtil) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot) {
        const page = route.queryParams['page'] ? route.queryParams['page'] : '1';
        const sort = route.queryParams['sort'] ? route.queryParams['sort'] : 'id,asc';
        return {
            page: this.paginationUtil.parsePage(page),
            predicate: this.paginationUtil.parsePredicate(sort),
            ascending: this.paginationUtil.parseAscending(sort)
      };
    }
}

export const assetstatusRoute: Routes = [
    {
        path: 'assetstatus',
        component: AssetstatusComponent,
        resolve: {
            'pagingParams': AssetstatusResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.assetstatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'assetstatus/:id',
        component: AssetstatusDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.assetstatus.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const assetstatusPopupRoute: Routes = [
    {
        path: 'assetstatus-new',
        component: AssetstatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.assetstatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'assetstatus/:id/edit',
        component: AssetstatusPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.assetstatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'assetstatus/:id/delete',
        component: AssetstatusDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.assetstatus.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
