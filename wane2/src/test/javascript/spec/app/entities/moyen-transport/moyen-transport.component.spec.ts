/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { MoyenTransportComponent } from 'app/entities/moyen-transport/moyen-transport.component';
import { MoyenTransportService } from 'app/entities/moyen-transport/moyen-transport.service';
import { MoyenTransport } from 'app/shared/model/moyen-transport.model';

describe('Component Tests', () => {
    describe('MoyenTransport Management Component', () => {
        let comp: MoyenTransportComponent;
        let fixture: ComponentFixture<MoyenTransportComponent>;
        let service: MoyenTransportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [MoyenTransportComponent],
                providers: []
            })
                .overrideTemplate(MoyenTransportComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MoyenTransportComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoyenTransportService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new MoyenTransport(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.moyenTransports[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
