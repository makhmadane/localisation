/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { TypeRglmentDetailComponent } from 'app/entities/type-rglment/type-rglment-detail.component';
import { TypeRglment } from 'app/shared/model/type-rglment.model';

describe('Component Tests', () => {
    describe('TypeRglment Management Detail Component', () => {
        let comp: TypeRglmentDetailComponent;
        let fixture: ComponentFixture<TypeRglmentDetailComponent>;
        const route = ({ data: of({ typeRglment: new TypeRglment(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [TypeRglmentDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeRglmentDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeRglmentDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeRglment).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
