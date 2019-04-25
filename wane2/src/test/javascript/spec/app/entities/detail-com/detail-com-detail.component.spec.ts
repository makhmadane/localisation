/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { DetailComDetailComponent } from 'app/entities/detail-com/detail-com-detail.component';
import { DetailCom } from 'app/shared/model/detail-com.model';

describe('Component Tests', () => {
    describe('DetailCom Management Detail Component', () => {
        let comp: DetailComDetailComponent;
        let fixture: ComponentFixture<DetailComDetailComponent>;
        const route = ({ data: of({ detailCom: new DetailCom(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [DetailComDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(DetailComDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DetailComDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.detailCom).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
