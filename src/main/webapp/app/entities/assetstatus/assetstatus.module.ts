import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApprefactorySharedModule } from '../../shared';
import {
    AssetstatusService,
    AssetstatusPopupService,
    AssetstatusComponent,
    AssetstatusDetailComponent,
    AssetstatusDialogComponent,
    AssetstatusPopupComponent,
    AssetstatusDeletePopupComponent,
    AssetstatusDeleteDialogComponent,
    assetstatusRoute,
    assetstatusPopupRoute,
    AssetstatusResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...assetstatusRoute,
    ...assetstatusPopupRoute,
];

@NgModule({
    imports: [
        ApprefactorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        AssetstatusComponent,
        AssetstatusDetailComponent,
        AssetstatusDialogComponent,
        AssetstatusDeleteDialogComponent,
        AssetstatusPopupComponent,
        AssetstatusDeletePopupComponent,
    ],
    entryComponents: [
        AssetstatusComponent,
        AssetstatusDialogComponent,
        AssetstatusPopupComponent,
        AssetstatusDeleteDialogComponent,
        AssetstatusDeletePopupComponent,
    ],
    providers: [
        AssetstatusService,
        AssetstatusPopupService,
        AssetstatusResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApprefactoryAssetstatusModule {}
