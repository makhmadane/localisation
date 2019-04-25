/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { DepotComponent } from 'app/entities/depot/depot.component';
import { DepotService } from 'app/entities/depot/depot.service';
import { Depot } from 'app/shared/model/depot.model';

describe('Component Tests', () => {
    describe('Depot Management Component', () => {
        let comp: DepotComponent;
        let fixture: ComponentFixture<DepotComponent>;
        let service: DepotService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [DepotComponent],
                providers: []
            })
                .overrideTemplate(DepotComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(DepotComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepotService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Depot(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.depots[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
