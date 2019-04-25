/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { MetierComponent } from 'app/entities/metier/metier.component';
import { MetierService } from 'app/entities/metier/metier.service';
import { Metier } from 'app/shared/model/metier.model';

describe('Component Tests', () => {
    describe('Metier Management Component', () => {
        let comp: MetierComponent;
        let fixture: ComponentFixture<MetierComponent>;
        let service: MetierService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [MetierComponent],
                providers: []
            })
                .overrideTemplate(MetierComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(MetierComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(MetierService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Metier(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.metiers[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
