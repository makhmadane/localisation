/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { ParfumComponent } from 'app/entities/parfum/parfum.component';
import { ParfumService } from 'app/entities/parfum/parfum.service';
import { Parfum } from 'app/shared/model/parfum.model';

describe('Component Tests', () => {
    describe('Parfum Management Component', () => {
        let comp: ParfumComponent;
        let fixture: ComponentFixture<ParfumComponent>;
        let service: ParfumService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [ParfumComponent],
                providers: []
            })
                .overrideTemplate(ParfumComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ParfumComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ParfumService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Parfum(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.parfums[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
