/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { BonLivraisonComponent } from 'app/entities/bon-livraison/bon-livraison.component';
import { BonLivraisonService } from 'app/entities/bon-livraison/bon-livraison.service';
import { BonLivraison } from 'app/shared/model/bon-livraison.model';

describe('Component Tests', () => {
    describe('BonLivraison Management Component', () => {
        let comp: BonLivraisonComponent;
        let fixture: ComponentFixture<BonLivraisonComponent>;
        let service: BonLivraisonService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [BonLivraisonComponent],
                providers: []
            })
                .overrideTemplate(BonLivraisonComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(BonLivraisonComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BonLivraisonService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new BonLivraison(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.bonLivraisons[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
