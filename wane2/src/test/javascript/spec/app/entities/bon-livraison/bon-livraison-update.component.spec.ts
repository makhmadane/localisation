/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { BonLivraisonUpdateComponent } from 'app/entities/bon-livraison/bon-livraison-update.component';
import { BonLivraisonService } from 'app/entities/bon-livraison/bon-livraison.service';
import { BonLivraison } from 'app/shared/model/bon-livraison.model';

describe('Component Tests', () => {
    describe('BonLivraison Management Update Component', () => {
        let comp: BonLivraisonUpdateComponent;
        let fixture: ComponentFixture<BonLivraisonUpdateComponent>;
        let service: BonLivraisonService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [BonLivraisonUpdateComponent]
            })
                .overrideTemplate(BonLivraisonUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BonLivraisonUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BonLivraisonService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new BonLivraison(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bonLivraison = entity;
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
                    const entity = new BonLivraison();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.bonLivraison = entity;
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
