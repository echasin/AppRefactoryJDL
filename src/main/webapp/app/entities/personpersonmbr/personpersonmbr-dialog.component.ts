import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Response } from '@angular/http';

import { Observable } from 'rxjs/Rx';
import { NgbActiveModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { EventManager, AlertService } from 'ng-jhipster';

import { Personpersonmbr } from './personpersonmbr.model';
import { PersonpersonmbrPopupService } from './personpersonmbr-popup.service';
import { PersonpersonmbrService } from './personpersonmbr.service';
import { Person, PersonService } from '../person';
import { ResponseWrapper } from '../../shared';

@Component({
    selector: 'jhi-personpersonmbr-dialog',
    templateUrl: './personpersonmbr-dialog.component.html'
})
export class PersonpersonmbrDialogComponent implements OnInit {

    personpersonmbr: Personpersonmbr;
    authorities: any[];
    isSaving: boolean;

    people: Person[];

    constructor(
        public activeModal: NgbActiveModal,
        private alertService: AlertService,
        private personpersonmbrService: PersonpersonmbrService,
        private personService: PersonService,
        private eventManager: EventManager
    ) {
    }

    ngOnInit() {
        this.isSaving = false;
        this.authorities = ['ROLE_USER', 'ROLE_ADMIN'];
        this.personService.query()
            .subscribe((res: ResponseWrapper) => { this.people = res.json; }, (res: ResponseWrapper) => this.onError(res.json));
    }
    clear() {
        this.activeModal.dismiss('cancel');
    }

    save() {
        this.isSaving = true;
        if (this.personpersonmbr.id !== undefined) {
            this.subscribeToSaveResponse(
                this.personpersonmbrService.update(this.personpersonmbr));
        } else {
            this.subscribeToSaveResponse(
                this.personpersonmbrService.create(this.personpersonmbr));
        }
    }

    private subscribeToSaveResponse(result: Observable<Personpersonmbr>) {
        result.subscribe((res: Personpersonmbr) =>
            this.onSaveSuccess(res), (res: Response) => this.onSaveError(res));
    }

    private onSaveSuccess(result: Personpersonmbr) {
        this.eventManager.broadcast({ name: 'personpersonmbrListModification', content: 'OK'});
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

    trackPersonById(index: number, item: Person) {
        return item.id;
    }
}

@Component({
    selector: 'jhi-personpersonmbr-popup',
    template: ''
})
export class PersonpersonmbrPopupComponent implements OnInit, OnDestroy {

    modalRef: NgbModalRef;
    routeSub: any;

    constructor(
        private route: ActivatedRoute,
        private personpersonmbrPopupService: PersonpersonmbrPopupService
    ) {}

    ngOnInit() {
        this.routeSub = this.route.params.subscribe((params) => {
            if ( params['id'] ) {
                this.modalRef = this.personpersonmbrPopupService
                    .open(PersonpersonmbrDialogComponent, params['id']);
            } else {
                this.modalRef = this.personpersonmbrPopupService
                    .open(PersonpersonmbrDialogComponent);
            }
        });
    }

    ngOnDestroy() {
        this.routeSub.unsubscribe();
    }
}
