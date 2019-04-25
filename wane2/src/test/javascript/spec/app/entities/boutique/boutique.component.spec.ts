/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { BoutiqueComponent } from 'app/entities/boutique/boutique.component';
import { BoutiqueService } from 'app/entities/boutique/boutique.service';
import { Boutique } from 'app/shared/model/boutique.model';

describe('Component Tests', () => {
    describe('Boutique Management Component', () => {
        let comp: BoutiqueComponent;
        let fixture: ComponentFixture<BoutiqueComponent>;
        let service: BoutiqueService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [BoutiqueComponent],
                providers: []
            })
                .overrideTemplate(BoutiqueComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BoutiqueComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BoutiqueService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Boutique(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.boutiques[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
