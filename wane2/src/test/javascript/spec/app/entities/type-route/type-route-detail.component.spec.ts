/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { TypeRouteDetailComponent } from 'app/entities/type-route/type-route-detail.component';
import { TypeRoute } from 'app/shared/model/type-route.model';

describe('Component Tests', () => {
    describe('TypeRoute Management Detail Component', () => {
        let comp: TypeRouteDetailComponent;
        let fixture: ComponentFixture<TypeRouteDetailComponent>;
        const route = ({ data: of({ typeRoute: new TypeRoute(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [TypeRouteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeRouteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeRouteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeRoute).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
