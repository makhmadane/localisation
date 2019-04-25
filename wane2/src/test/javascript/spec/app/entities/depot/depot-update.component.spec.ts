/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { DepotUpdateComponent } from 'app/entities/depot/depot-update.component';
import { DepotService } from 'app/entities/depot/depot.service';
import { Depot } from 'app/shared/model/depot.model';

describe('Component Tests', () => {
    describe('Depot Management Update Component', () => {
        let comp: DepotUpdateComponent;
        let fixture: ComponentFixture<DepotUpdateComponent>;
        let service: DepotService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [DepotUpdateComponent]
            })
                .overrideTemplate(DepotUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepotUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepotService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Depot(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.depot = entity;
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
                    const entity = new Depot();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.depot = entity;
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
