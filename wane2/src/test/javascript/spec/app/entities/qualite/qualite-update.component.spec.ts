/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { QualiteUpdateComponent } from 'app/entities/qualite/qualite-update.component';
import { QualiteService } from 'app/entities/qualite/qualite.service';
import { Qualite } from 'app/shared/model/qualite.model';

describe('Component Tests', () => {
    describe('Qualite Management Update Component', () => {
        let comp: QualiteUpdateComponent;
        let fixture: ComponentFixture<QualiteUpdateComponent>;
        let service: QualiteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [QualiteUpdateComponent]
            })
                .overrideTemplate(QualiteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(QualiteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QualiteService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Qualite(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.qualite = entity;
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
                    const entity = new Qualite();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.qualite = entity;
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
