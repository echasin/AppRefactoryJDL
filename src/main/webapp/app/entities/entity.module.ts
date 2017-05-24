import { NgModule, CUSTOM_ELEMENTS_SCHEMA } from '@angular/core';

import { ApprefactoryPersonModule } from './person/person.module';
import { ApprefactoryOrganizationModule } from './organization/organization.module';
import { ApprefactoryPersonpersonmbrModule } from './personpersonmbr/personpersonmbr.module';
import { ApprefactoryAssetModule } from './asset/asset.module';
import { ApprefactoryAssetstatusModule } from './assetstatus/assetstatus.module';
/* jhipster-needle-add-entity-module-import - JHipster will add entity modules imports here */

@NgModule({
    imports: [
        ApprefactoryPersonModule,
        ApprefactoryOrganizationModule,
        ApprefactoryPersonpersonmbrModule,
        ApprefactoryAssetModule,
        ApprefactoryAssetstatusModule,
        /* jhipster-needle-add-entity-module - JHipster will add entity modules here */
    ],
    declarations: [],
    entryComponents: [],
    providers: [],
    schemas: [CUSTOM_ELEMENTS_SCHEMA]
})
export class ApprefactoryEntityModule {}
