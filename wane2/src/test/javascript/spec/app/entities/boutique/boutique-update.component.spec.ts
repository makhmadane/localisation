/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { BoutiqueUpdateComponent } from 'app/entities/boutique/boutique-update.component';
import { BoutiqueService } from 'app/entities/boutique/boutique.service';
import { Boutique } from 'app/shared/model/boutique.model';

describe('Component Tests', () => {
    describe('Boutique Management Update Component', () => {
        let comp: BoutiqueUpdateComponent;
        let fixture: ComponentFixture<BoutiqueUpdateComponent>;
        let service: BoutiqueService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [BoutiqueUpdateComponent]
            })
                .overrideTemplate(BoutiqueUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BoutiqueUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BoutiqueService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Boutique(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.boutique = entity;
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
                    const entity = new Boutique();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.boutique = entity;
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
