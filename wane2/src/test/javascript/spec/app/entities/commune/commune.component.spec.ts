/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { CommuneComponent } from 'app/entities/commune/commune.component';
import { CommuneService } from 'app/entities/commune/commune.service';
import { Commune } from 'app/shared/model/commune.model';

describe('Component Tests', () => {
    describe('Commune Management Component', () => {
        let comp: CommuneComponent;
        let fixture: ComponentFixture<CommuneComponent>;
        let service: CommuneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [CommuneComponent],
                providers: []
            })
                .overrideTemplate(CommuneComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommuneComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommuneService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Commune(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.communes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
