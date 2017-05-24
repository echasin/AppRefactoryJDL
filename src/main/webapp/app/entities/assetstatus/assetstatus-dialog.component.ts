import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Assetstatus } from './assetstatus.model';
import { AssetstatusPopupService } from './assetstatus-popup.service';
import { AssetstatusService } from './assetstatus.service';

@Component({
    selector: 'jhi-assetstatus-dialog',
    templateUrl: './assetstatus-dialog.component.html'
})
export class AssetstatusDialogComponent implements OnInit {

    assetstatus: Assetstatus;
    authorities: any[];
    isSaving: boolean;

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private assetstatusService: AssetstatusService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.assetstatus.id !== undefined) {
            this.subscribeToSaveResponse(
                this.assetstatusService.update(this.assetstatus));
        } else {
            this.subscribeToSaveResponse(
                this.assetstatusService.create(this.assetstatus));
        }
    }

    private subscribeToSaveResponse(result: Observable<Assetstatus>) {
        result.subscribe((res: Assetstatus) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Assetstatus) {
        this.eventManager.broadcast({ name: 'assetstatusListModification', content: 'OK'});
        this.isSaving = false;
        this.activeModal.dismiss(result);
    }

    private onSaveError(error) {
        try {
            error.json();
        } catch (exception) {
            error.message = error.text();
        }
        this.isSaving = false;
        this.onError(error);
    }

    private onError(error) {
        this.alertService.error(error.message, null, null);
    }
}

@Component({
    selector: 'jhi-assetstatus-popup',
    template: ''
})
export class AssetstatusPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private assetstatusPopupService: AssetstatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.assetstatusPopupService
                    .open(AssetstatusDialogComponent, params['id']);
            } else {
                this.modalRef = this.assetstatusPopupService
                    .open(AssetstatusDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
