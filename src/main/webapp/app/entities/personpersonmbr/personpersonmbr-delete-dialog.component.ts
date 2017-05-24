import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager } from 'ng-jhipster';

import { Personpersonmbr } from './personpersonmbr.model';
import { PersonpersonmbrPopupService } from './personpersonmbr-popup.service';
import { PersonpersonmbrService } from './personpersonmbr.service';

@Component({
    selector: 'jhi-personpersonmbr-delete-dialog',
    templateUrl: './personpersonmbr-delete-dialog.component.html'
})
export class PersonpersonmbrDeleteDialogComponent {

    personpersonmbr: Personpersonmbr;

    constructor(
        private personpersonmbrService: PersonpersonmbrService,
        public activeModal: NgbActiveModal,
        private eventManager: EventManager
    ) {
    }

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.personpersonmbrService.delete(id).subscribe((response) => {
            this.eventManager.broadcast({
                name: 'personpersonmbrListModification',
                content: 'Deleted an personpersonmbr'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-personpersonmbr-delete-popup',
    template: ''
})
export class PersonpersonmbrDeletePopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personpersonmbrPopupService: PersonpersonmbrPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            this.modalRef = this.personpersonmbrPopupService
                .open(PersonpersonmbrDeleteDialogComponent, params['id']);
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
