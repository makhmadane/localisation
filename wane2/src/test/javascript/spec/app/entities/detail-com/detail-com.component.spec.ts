/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { DetailComComponent } from 'app/entities/detail-com/detail-com.component';
import { DetailComService } from 'app/entities/detail-com/detail-com.service';
import { DetailCom } from 'app/shared/model/detail-com.model';

describe('Component Tests', () => {
    describe('DetailCom Management Component', () => {
        let comp: DetailComComponent;
        let fixture: ComponentFixture<DetailComComponent>;
        let service: DetailComService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [DetailComComponent],
                providers: []
            })
                .overrideTemplate(DetailComComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DetailComComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetailComService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new DetailCom(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.detailComs[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
