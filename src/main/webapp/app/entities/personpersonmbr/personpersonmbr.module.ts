import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';
import { RouterModule } from '@angular/router';

import { ApprefactorySharedModule } from '../../shared';
import {
    PersonpersonmbrService,
    PersonpersonmbrPopupService,
    PersonpersonmbrComponent,
    PersonpersonmbrDetailComponent,
    PersonpersonmbrDialogComponent,
    PersonpersonmbrPopupComponent,
    PersonpersonmbrDeletePopupComponent,
    PersonpersonmbrDeleteDialogComponent,
    personpersonmbrRoute,
    personpersonmbrPopupRoute,
    PersonpersonmbrResolvePagingParams,
} from './';

const ENTITY_STATES = [
    ...personpersonmbrRoute,
    ...personpersonmbrPopupRoute,
];

@NgModule({
    imports: [
        ApprefactorySharedModule,
        RouterModule.forRoot(ENTITY_STATES, { useHash: true })
    ],
    declarations: [
        PersonpersonmbrComponent,
        PersonpersonmbrDetailComponent,
        PersonpersonmbrDialogComponent,
        PersonpersonmbrDeleteDialogComponent,
        PersonpersonmbrPopupComponent,
        PersonpersonmbrDeletePopupComponent,
    ],
    entryComponents: [
        PersonpersonmbrComponent,
        PersonpersonmbrDialogComponent,
        PersonpersonmbrPopupComponent,
        PersonpersonmbrDeleteDialogComponent,
        PersonpersonmbrDeletePopupComponent,
    ],
    providers: [
        PersonpersonmbrService,
        PersonpersonmbrPopupService,
        PersonpersonmbrResolvePagingParams,
    ],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApprefactoryPersonpersonmbrModule {}
