/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { StockInitialUpdateComponent } from 'app/entities/stock-initial/stock-initial-update.component';
import { StockInitialService } from 'app/entities/stock-initial/stock-initial.service';
import { StockInitial } from 'app/shared/model/stock-initial.model';

describe('Component Tests', () => {
    describe('StockInitial Management Update Component', () => {
        let comp: StockInitialUpdateComponent;
        let fixture: ComponentFixture<StockInitialUpdateComponent>;
        let service: StockInitialService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [StockInitialUpdateComponent]
            })
                .overrideTemplate(StockInitialUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StockInitialUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockInitialService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new StockInitial(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stockInitial = entity;
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
                    const entity = new StockInitial();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.stockInitial = entity;
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
