import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Assetstatus } from './assetstatus.model';
import { AssetstatusService } from './assetstatus.service';

@Component({
    selector: 'jhi-assetstatus-detail',
    templateUrl: './assetstatus-detail.component.html'
})
export class AssetstatusDetailComponent implements OnInit, OnDestroy {

    assetstatus: Assetstatus;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private assetstatusService: AssetstatusService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInAssetstatuses();
    }

    load(id) {
        this.assetstatusService.find(id).subscribe((assetstatus) => {
            this.assetstatus = assetstatus;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInAssetstatuses() {
        this.eventSubscriber = this.eventManager.subscribe(
            'assetstatusListModification',
            (response) => this.load(this.assetstatus.id)
        );
    }
}
