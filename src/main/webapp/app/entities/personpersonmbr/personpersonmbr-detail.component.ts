import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs/Rx';
import { EventManager  } from 'ng-jhipster';

import { Personpersonmbr } from './personpersonmbr.model';
import { PersonpersonmbrService } from './personpersonmbr.service';

@Component({
    selector: 'jhi-personpersonmbr-detail',
    templateUrl: './personpersonmbr-detail.component.html'
})
export class PersonpersonmbrDetailComponent implements OnInit, OnDestroy {

    personpersonmbr: Personpersonmbr;
    private subscription: Subscription;
    private eventSubscriber: Subscription;

    constructor(
        private eventManager: EventManager,
        private personpersonmbrService: PersonpersonmbrService,
        private route: ActivatedRoute
    ) {
    }

    ngOnInit() {
        this.subscription = this.route.params.subscribe((params) => {
            this.load(params['id']);
        });
        this.registerChangeInPersonpersonmbrs();
    }

    load(id) {
        this.personpersonmbrService.find(id).subscribe((personpersonmbr) => {
            this.personpersonmbr = personpersonmbr;
        });
    }
    previousState() {
        window.history.back();
    }

    ngOnDestroy() {
        this.subscription.unsubscribe();
        this.eventManager.destroy(this.eventSubscriber);
    }

    registerChangeInPersonpersonmbrs() {
        this.eventSubscriber = this.eventManager.subscribe(
            'personpersonmbrListModification',
            (response) => this.load(this.personpersonmbr.id)
        );
    }
}
