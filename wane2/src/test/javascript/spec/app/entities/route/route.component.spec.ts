/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { RouteComponent } from 'app/entities/route/route.component';
import { RouteService } from 'app/entities/route/route.service';
import { Route } from 'app/shared/model/route.model';

describe('Component Tests', () => {
    describe('Route Management Component', () => {
        let comp: RouteComponent;
        let fixture: ComponentFixture<RouteComponent>;
        let service: RouteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [RouteComponent],
                providers: []
            })
                .overrideTemplate(RouteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(RouteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(RouteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Route(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.routes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
