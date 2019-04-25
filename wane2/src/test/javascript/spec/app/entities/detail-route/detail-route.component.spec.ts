/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { DetailRouteComponent } from 'app/entities/detail-route/detail-route.component';
import { DetailRouteService } from 'app/entities/detail-route/detail-route.service';
import { DetailRoute } from 'app/shared/model/detail-route.model';

describe('Component Tests', () => {
    describe('DetailRoute Management Component', () => {
        let comp: DetailRouteComponent;
        let fixture: ComponentFixture<DetailRouteComponent>;
        let service: DetailRouteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [DetailRouteComponent],
                providers: []
            })
                .overrideTemplate(DetailRouteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DetailRouteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetailRouteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DetailRoute(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.detailRoutes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
