/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { TypeRglmentUpdateComponent } from 'app/entities/type-rglment/type-rglment-update.component';
import { TypeRglmentService } from 'app/entities/type-rglment/type-rglment.service';
import { TypeRglment } from 'app/shared/model/type-rglment.model';

describe('Component Tests', () => {
    describe('TypeRglment Management Update Component', () => {
        let comp: TypeRglmentUpdateComponent;
        let fixture: ComponentFixture<TypeRglmentUpdateComponent>;
        let service: TypeRglmentService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [TypeRglmentUpdateComponent]
            })
                .overrideTemplate(TypeRglmentUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeRglmentUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeRglmentService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TypeRglment(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typeRglment = entity;
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
                    const entity = new TypeRglment();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typeRglment = entity;
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
