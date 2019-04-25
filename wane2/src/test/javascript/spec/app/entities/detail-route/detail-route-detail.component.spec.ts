/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { DetailRouteDetailComponent } from 'app/entities/detail-route/detail-route-detail.component';
import { DetailRoute } from 'app/shared/model/detail-route.model';

describe('Component Tests', () => {
    describe('DetailRoute Management Detail Component', () => {
        let comp: DetailRouteDetailComponent;
        let fixture: ComponentFixture<DetailRouteDetailComponent>;
        const route = ({ data: of({ detailRoute: new DetailRoute(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [DetailRouteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DetailRouteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DetailRouteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.detailRoute).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
