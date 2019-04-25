/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { StockInitialDetailComponent } from 'app/entities/stock-initial/stock-initial-detail.component';
import { StockInitial } from 'app/shared/model/stock-initial.model';

describe('Component Tests', () => {
    describe('StockInitial Management Detail Component', () => {
        let comp: StockInitialDetailComponent;
        let fixture: ComponentFixture<StockInitialDetailComponent>;
        const route = ({ data: of({ stockInitial: new StockInitial(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [StockInitialDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(StockInitialDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StockInitialDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.stockInitial).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
