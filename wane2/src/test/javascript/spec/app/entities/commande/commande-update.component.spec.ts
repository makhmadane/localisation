/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { CommandeUpdateComponent } from 'app/entities/commande/commande-update.component';
import { CommandeService } from 'app/entities/commande/commande.service';
import { Commande } from 'app/shared/model/commande.model';

describe('Component Tests', () => {
    describe('Commande Management Update Component', () => {
        let comp: CommandeUpdateComponent;
        let fixture: ComponentFixture<CommandeUpdateComponent>;
        let service: CommandeService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [CommandeUpdateComponent]
            })
                .overrideTemplate(CommandeUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(CommandeUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(CommandeService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Commande(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.commande = entity;
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
                    const entity = new Commande();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.commande = entity;
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
