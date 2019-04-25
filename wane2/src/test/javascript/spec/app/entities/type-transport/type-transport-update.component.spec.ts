/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { TypeTransportUpdateComponent } from 'app/entities/type-transport/type-transport-update.component';
import { TypeTransportService } from 'app/entities/type-transport/type-transport.service';
import { TypeTransport } from 'app/shared/model/type-transport.model';

describe('Component Tests', () => {
    describe('TypeTransport Management Update Component', () => {
        let comp: TypeTransportUpdateComponent;
        let fixture: ComponentFixture<TypeTransportUpdateComponent>;
        let service: TypeTransportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [TypeTransportUpdateComponent]
            })
                .overrideTemplate(TypeTransportUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TypeTransportUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeTransportService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new TypeTransport(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typeTransport = entity;
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
                    const entity = new TypeTransport();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.typeTransport = entity;
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
