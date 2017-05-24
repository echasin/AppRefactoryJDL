import { Injectable } from '@angular/core';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes, CanActivate } from '@angular/router';

import { UserRouteAccessService } from '../../shared';
import { PaginationUtil } from 'ng-jhipster';

import { PersonpersonmbrComponent } from './personpersonmbr.component';
import { PersonpersonmbrDetailComponent } from './personpersonmbr-detail.component';
import { PersonpersonmbrPopupComponent } from './personpersonmbr-dialog.component';
import { PersonpersonmbrDeletePopupComponent } from './personpersonmbr-delete-dialog.component';

import { Principal } from '../../shared';

@Injectable()
export class PersonpersonmbrResolvePagingParams implements Resolve<any> {

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

export const personpersonmbrRoute: Routes = [
    {
        path: 'personpersonmbr',
        component: PersonpersonmbrComponent,
        resolve: {
            'pagingParams': PersonpersonmbrResolvePagingParams
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.personpersonmbr.home.title'
        },
        canActivate: [UserRouteAccessService]
    }, {
        path: 'personpersonmbr/:id',
        component: PersonpersonmbrDetailComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.personpersonmbr.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const personpersonmbrPopupRoute: Routes = [
    {
        path: 'personpersonmbr-new',
        component: PersonpersonmbrPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.personpersonmbr.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'personpersonmbr/:id/edit',
        component: PersonpersonmbrPopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.personpersonmbr.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    },
    {
        path: 'personpersonmbr/:id/delete',
        component: PersonpersonmbrDeletePopupComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'apprefactoryApp.personpersonmbr.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
