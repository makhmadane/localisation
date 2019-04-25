/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { MoyenTransportUpdateComponent } from 'app/entities/moyen-transport/moyen-transport-update.component';
import { MoyenTransportService } from 'app/entities/moyen-transport/moyen-transport.service';
import { MoyenTransport } from 'app/shared/model/moyen-transport.model';

describe('Component Tests', () => {
    describe('MoyenTransport Management Update Component', () => {
        let comp: MoyenTransportUpdateComponent;
        let fixture: ComponentFixture<MoyenTransportUpdateComponent>;
        let service: MoyenTransportService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [MoyenTransportUpdateComponent]
            })
                .overrideTemplate(MoyenTransportUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MoyenTransportUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MoyenTransportService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new MoyenTransport(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.moyenTransport = entity;
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
                    const entity = new MoyenTransport();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.moyenTransport = entity;
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
