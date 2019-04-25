/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { TypeRglmentComponent } from 'app/entities/type-rglment/type-rglment.component';
import { TypeRglmentService } from 'app/entities/type-rglment/type-rglment.service';
import { TypeRglment } from 'app/shared/model/type-rglment.model';

describe('Component Tests', () => {
    describe('TypeRglment Management Component', () => {
        let comp: TypeRglmentComponent;
        let fixture: ComponentFixture<TypeRglmentComponent>;
        let service: TypeRglmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [TypeRglmentComponent],
                providers: []
            })
                .overrideTemplate(TypeRglmentComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeRglmentComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeRglmentService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TypeRglment(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.typeRglments[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
