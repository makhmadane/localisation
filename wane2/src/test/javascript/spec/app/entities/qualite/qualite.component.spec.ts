/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { QualiteComponent } from 'app/entities/qualite/qualite.component';
import { QualiteService } from 'app/entities/qualite/qualite.service';
import { Qualite } from 'app/shared/model/qualite.model';

describe('Component Tests', () => {
    describe('Qualite Management Component', () => {
        let comp: QualiteComponent;
        let fixture: ComponentFixture<QualiteComponent>;
        let service: QualiteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [QualiteComponent],
                providers: []
            })
                .overrideTemplate(QualiteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QualiteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QualiteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Qualite(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.qualites[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
