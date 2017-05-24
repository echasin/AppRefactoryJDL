import { ComponentFixture, TestBed, async, inject } from '@angular/core/testing';
import { OnInit } from '@angular/core';
import { DatePipe } from '@angular/common';
import { ActivatedRoute } from '@angular/router';
import { Observable } from 'rxjs/Rx';
import { DateUtils, DataUtils, EventManager } from 'ng-jhipster';
import { ApprefactoryTestModule } from '../../../test.module';
import { MockActivatedRoute } from '../../../helpers/mock-route.service';
import { PersonpersonmbrDetailComponent } from '../../../../../../main/webapp/app/entities/personpersonmbr/personpersonmbr-detail.component';
import { PersonpersonmbrService } from '../../../../../../main/webapp/app/entities/personpersonmbr/personpersonmbr.service';
import { Personpersonmbr } from '../../../../../../main/webapp/app/entities/personpersonmbr/personpersonmbr.model';

describe('Component Tests', () => {

    describe('Personpersonmbr Management Detail Component', () => {
        let comp: PersonpersonmbrDetailComponent;
        let fixture: ComponentFixture<PersonpersonmbrDetailComponent>;
        let service: PersonpersonmbrService;

        beforeEach(async(() => {
            TestBed.configureTestingModule({
                imports: [ApprefactoryTestModule],
                declarations: [PersonpersonmbrDetailComponent],
                providers: [
                    DateUtils,
                    DataUtils,
                    DatePipe,
                    {
                        provide: ActivatedRoute,
                        useValue: new MockActivatedRoute({id: 123})
                    },
                    PersonpersonmbrService,
                    EventManager
                ]
            }).overrideComponent(PersonpersonmbrDetailComponent, {
                set: {
                    template: ''
                }
            }).compileComponents();
        }));

        beforeEach(() => {
            fixture = TestBed.createComponent(PersonpersonmbrDetailComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(PersonpersonmbrService);
        });


        describe('OnInit', () => {
            it('Should call load all on init', () => {
            // GIVEN

            spyOn(service, 'find').and.returnValue(Observable.of(new Personpersonmbr(10)));

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.find).toHaveBeenCalledWith(123);
            expect(comp.personpersonmbr).toEqual(jasmine.objectContaining({id:10}));
            });
        });
    });

});
