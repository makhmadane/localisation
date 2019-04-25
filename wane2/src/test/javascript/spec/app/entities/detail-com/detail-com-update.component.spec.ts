/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, fakeAsync, tick } from '@angular/core/testing';
import { HttpResponse } from '@angular/common/http';
import { Observable, of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { DetailComUpdateComponent } from 'app/entities/detail-com/detail-com-update.component';
import { DetailComService } from 'app/entities/detail-com/detail-com.service';
import { DetailCom } from 'app/shared/model/detail-com.model';

describe('Component Tests', () => {
    describe('DetailCom Management Update Component', () => {
        let comp: DetailComUpdateComponent;
        let fixture: ComponentFixture<DetailComUpdateComponent>;
        let service: DetailComService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [DetailComUpdateComponent]
            })
                .overrideTemplate(DetailComUpdateComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DetailComUpdateComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetailComService);
        });

        describe('save', () => {
            it(
                'Should call update service on save for existing entity',
                fakeAsync(() => {
                    // GIVEN
                    const entity = new DetailCom(123);
                    spyOn(service, 'update').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.detailCom = entity;
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
                    const entity = new DetailCom();
                    spyOn(service, 'create').and.returnValue(of(new HttpResponse({ body: entity })));
                    comp.detailCom = entity;
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
