import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { AssetComponent } from './asset.component';
import { AssetDetailComponent } from './asset-detail.component';
import { AssetPopupComponent } from './asset-dialog.component';
import { AssetDeletePopupComponent } from './asset-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class AssetResolvePagingParams implements Resolve<any> {

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

export const assetRoute: Routes = [
    {
        path: 'asset',
        component: AssetComponent,
        resolve: {
            'pagingParams': AssetResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.asset.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'asset/:id',
        component: AssetDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.asset.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const assetPopupRoute: Routes = [
    {
        path: 'asset-new',
        component: AssetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.asset.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'asset/:id/edit',
        component: AssetPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.asset.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'asset/:id/delete',
        component: AssetDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.asset.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
