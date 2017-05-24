import { Injectable, Component } from '@angular/core';
import { Router } from '@angular/router';
import { NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { Assetstatus } from './assetstatus.model';
import { AssetstatusService } from './assetstatus.service';
@Injectable()
export class AssetstatusPopupService {
    private isOpen = false;
    constructor(
        private modalService: NgbModal,
        private router: Router,
        private assetstatusService: AssetstatusService

    ) {}

    open(component: Component, id?: number | any): NgbModalRef {
        if (this.isOpen) {
            return;
        }
        this.isOpen = true;

        if (id) {
            this.assetstatusService.find(id).subscribe((assetstatus) => {
                this.assetstatusModalRef(component, assetstatus);
            });
        } else {
            return this.assetstatusModalRef(component, new Assetstatus());
        }
    }

    assetstatusModalRef(component: Component, assetstatus: Assetstatus): NgbModalRef {
        const modalRef = this.modalService.open(component, { size: 'lg', backdrop: 'static'});
        modalRef.componentInstance.assetstatus = assetstatus;
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
