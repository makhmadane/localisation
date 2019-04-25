/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { TypeRouteComponent } from 'app/entities/type-route/type-route.component';
import { TypeRouteService } from 'app/entities/type-route/type-route.service';
import { TypeRoute } from 'app/shared/model/type-route.model';

describe('Component Tests', () => {
    describe('TypeRoute Management Component', () => {
        let comp: TypeRouteComponent;
        let fixture: ComponentFixture<TypeRouteComponent>;
        let service: TypeRouteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [TypeRouteComponent],
                providers: []
            })
                .overrideTemplate(TypeRouteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeRouteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeRouteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new TypeRoute(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.typeRoutes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
