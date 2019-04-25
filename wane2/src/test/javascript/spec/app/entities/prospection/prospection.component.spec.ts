/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { ProspectionComponent } from 'app/entities/prospection/prospection.component';
import { ProspectionService } from 'app/entities/prospection/prospection.service';
import { Prospection } from 'app/shared/model/prospection.model';

describe('Component Tests', () => {
    describe('Prospection Management Component', () => {
        let comp: ProspectionComponent;
        let fixture: ComponentFixture<ProspectionComponent>;
        let service: ProspectionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [ProspectionComponent],
                providers: []
            })
                .overrideTemplate(ProspectionComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProspectionComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProspectionService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Prospection(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.prospections[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
