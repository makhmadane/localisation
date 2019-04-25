/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { TabletteUpdateComponent } from 'app/entities/tablette/tablette-update.component';
import { TabletteService } from 'app/entities/tablette/tablette.service';
import { Tablette } from 'app/shared/model/tablette.model';

describe('Component Tests', () => {
    describe('Tablette Management Update Component', () => {
        let comp: TabletteUpdateComponent;
        let fixture: ComponentFixture<TabletteUpdateComponent>;
        let service: TabletteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [TabletteUpdateComponent]
            })
                .overrideTemplate(TabletteUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TabletteUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TabletteService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new Tablette(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tablette = entity;
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
                    const entity = new Tablette();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.tablette = entity;
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
