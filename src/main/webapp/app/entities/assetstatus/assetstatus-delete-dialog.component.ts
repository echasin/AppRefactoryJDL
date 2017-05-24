import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Assetstatus } from './assetstatus.model';
import { AssetstatusPopupService } from './assetstatus-popup.service';
import { AssetstatusService } from './assetstatus.service';

@Component({
    selector: 'jhi-assetstatus-delete-dialog',
    templateUrl: './assetstatus-delete-dialog.component.html'
})
export class AssetstatusDeleteDialogComponent {

    assetstatus: Assetstatus;

    constructor(
        private assetstatusService: AssetstatusService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.assetstatusService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'assetstatusListModification',
                content: 'Deleted an assetstatus'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-assetstatus-delete-popup',
    template: ''
})
export class AssetstatusDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private assetstatusPopupService: AssetstatusPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.assetstatusPopupService
                .open(AssetstatusDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
