/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { ApproComponent } from 'app/entities/appro/appro.component';
import { ApproService } from 'app/entities/appro/appro.service';
import { Appro } from 'app/shared/model/appro.model';

describe('Component Tests', () => {
    describe('Appro Management Component', () => {
        let comp: ApproComponent;
        let fixture: ComponentFixture<ApproComponent>;
        let service: ApproService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [ApproComponent],
                providers: []
            })
                .overrideTemplate(ApproComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApproComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApproService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Appro(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.appros[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
