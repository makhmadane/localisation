/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { TypeTransportComponent } from 'app/entities/type-transport/type-transport.component';
import { TypeTransportService } from 'app/entities/type-transport/type-transport.service';
import { TypeTransport } from 'app/shared/model/type-transport.model';

describe('Component Tests', () => {
    describe('TypeTransport Management Component', () => {
        let comp: TypeTransportComponent;
        let fixture: ComponentFixture<TypeTransportComponent>;
        let service: TypeTransportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [TypeTransportComponent],
                providers: []
            })
                .overrideTemplate(TypeTransportComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeTransportComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeTransportService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TypeTransport(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.typeTransports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
