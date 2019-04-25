/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { CommuneUpdateComponent } from 'app/entities/commune/commune-update.component';
import { CommuneService } from 'app/entities/commune/commune.service';
import { Commune } from 'app/shared/model/commune.model';

describe('Component Tests', () => {
    describe('Commune Management Update Component', () => {
        let comp: CommuneUpdateComponent;
        let fixture: ComponentFixture<CommuneUpdateComponent>;
        let service: CommuneService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [CommuneUpdateComponent]
            })
                .overrideTemplate(CommuneUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommuneUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommuneService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Commune(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.commune = entity;
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
                    const entity = new Commune();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.commune = entity;
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
