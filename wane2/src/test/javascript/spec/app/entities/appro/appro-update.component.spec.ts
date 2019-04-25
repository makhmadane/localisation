/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { ApproUpdateComponent } from 'app/entities/appro/appro-update.component';
import { ApproService } from 'app/entities/appro/appro.service';
import { Appro } from 'app/shared/model/appro.model';

describe('Component Tests', () => {
    describe('Appro Management Update Component', () => {
        let comp: ApproUpdateComponent;
        let fixture: ComponentFixture<ApproUpdateComponent>;
        let service: ApproService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [ApproUpdateComponent]
            })
                .overrideTemplate(ApproUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(ApproUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(ApproService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Appro(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.appro = entity;
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
                    const entity = new Appro();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.appro = entity;
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
