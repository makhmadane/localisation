/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { ProspectionUpdateComponent } from 'app/entities/prospection/prospection-update.component';
import { ProspectionService } from 'app/entities/prospection/prospection.service';
import { Prospection } from 'app/shared/model/prospection.model';

describe('Component Tests', () => {
    describe('Prospection Management Update Component', () => {
        let comp: ProspectionUpdateComponent;
        let fixture: ComponentFixture<ProspectionUpdateComponent>;
        let service: ProspectionService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [ProspectionUpdateComponent]
            })
                .overrideTemplate(ProspectionUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ProspectionUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ProspectionService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Prospection(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.prospection = entity;
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
                    const entity = new Prospection();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.prospection = entity;
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
