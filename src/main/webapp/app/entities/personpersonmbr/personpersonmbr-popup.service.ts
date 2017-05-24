import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Personpersonmbr } from './personpersonmbr.model';
import { PersonpersonmbrService } from './personpersonmbr.service';
@Injectable()
export class PersonpersonmbrPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private personpersonmbrService: PersonpersonmbrService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.personpersonmbrService.find(id).subscribe((personpersonmbr) => {
                this.personpersonmbrModalRef(component, personpersonmbr);
            });
        } else {
            return this.personpersonmbrModalRef(component, new Personpersonmbr());
        }
    }

    personpersonmbrModalRef(component: Component, personpersonmbr: Personpersonmbr): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.personpersonmbr = personpersonmbr;
        modalRef.result.then((result) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        }, (reason) => {
            this.router.navigate([{ outlets: { popup: null }}], { replaceUrl: true });
            this.isOpen = false;
        });
        return modalRef;
    }
}
