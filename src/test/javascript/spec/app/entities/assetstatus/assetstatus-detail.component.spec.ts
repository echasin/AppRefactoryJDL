import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ApprefactoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { AssetstatusDetailComponent } from '../../../../../../main/webapp/app/entities/assetstatus/assetstatus-detail.component';
import { AssetstatusService } from '../../../../../../main/webapp/app/entities/assetstatus/assetstatus.service';
import { Assetstatus } from '../../../../../../main/webapp/app/entities/assetstatus/assetstatus.model';

describe('Component Tests', () => {

    describe('Assetstatus Management Detail Component', () => {
        let comp: AssetstatusDetailComponent;
        let fixture: ComponentFixture<AssetstatusDetailComponent>;
        let service: AssetstatusService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ApprefactoryTestModule],
                declarations: [AssetstatusDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    AssetstatusService,
                    EventManager
                ]
            }).overrideComponent(AssetstatusDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(AssetstatusDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(AssetstatusService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Assetstatus(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.assetstatus).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
