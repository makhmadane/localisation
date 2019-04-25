/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { TypeRouteUpdateComponent } from 'app/entities/type-route/type-route-update.component';
import { TypeRouteService } from 'app/entities/type-route/type-route.service';
import { TypeRoute } from 'app/shared/model/type-route.model';

describe('Component Tests', () => {
    describe('TypeRoute Management Update Component', () => {
        let comp: TypeRouteUpdateComponent;
        let fixture: ComponentFixture<TypeRouteUpdateComponent>;
        let service: TypeRouteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [TypeRouteUpdateComponent]
            })
                .overrideTemplate(TypeRouteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeRouteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeRouteService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TypeRoute(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typeRoute = entity;
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
                    const entity = new TypeRoute();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typeRoute = entity;
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
