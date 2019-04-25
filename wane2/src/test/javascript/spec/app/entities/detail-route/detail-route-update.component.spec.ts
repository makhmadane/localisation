/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { DetailRouteUpdateComponent } from 'app/entities/detail-route/detail-route-update.component';
import { DetailRouteService } from 'app/entities/detail-route/detail-route.service';
import { DetailRoute } from 'app/shared/model/detail-route.model';

describe('Component Tests', () => {
    describe('DetailRoute Management Update Component', () => {
        let comp: DetailRouteUpdateComponent;
        let fixture: ComponentFixture<DetailRouteUpdateComponent>;
        let service: DetailRouteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [DetailRouteUpdateComponent]
            })
                .overrideTemplate(DetailRouteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DetailRouteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetailRouteService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DetailRoute(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.detailRoute = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.update).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );

            it(
                'Should call create service on save for new entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DetailRoute();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.detailRoute = entity;
                    // WHEN
                    comp.save();
                    tick(); // simulate async

                    // THEN
                    expect(service.create).toHaveBeenCalledWith(entity);
                    expect(comp.isSaving).toEqual(false);
                })
            );
        });
    });
});
