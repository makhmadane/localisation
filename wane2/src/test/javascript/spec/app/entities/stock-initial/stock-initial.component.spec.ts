/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { StockInitialComponent } from 'app/entities/stock-initial/stock-initial.component';
import { StockInitialService } from 'app/entities/stock-initial/stock-initial.service';
import { StockInitial } from 'app/shared/model/stock-initial.model';

describe('Component Tests', () => {
    describe('StockInitial Management Component', () => {
        let comp: StockInitialComponent;
        let fixture: ComponentFixture<StockInitialComponent>;
        let service: StockInitialService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [StockInitialComponent],
                providers: []
            })
                .overrideTemplate(StockInitialComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(StockInitialComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockInitialService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new StockInitial(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.stockInitials[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
