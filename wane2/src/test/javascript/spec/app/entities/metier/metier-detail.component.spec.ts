/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { MetierDetailComponent } from 'app/entities/metier/metier-detail.component';
import { Metier } from 'app/shared/model/metier.model';

describe('Component Tests', () => {
    describe('Metier Management Detail Component', () => {
        let comp: MetierDetailComponent;
        let fixture: ComponentFixture<MetierDetailComponent>;
        const route = ({ data: of({ metier: new Metier(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [MetierDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MetierDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MetierDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.metier).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
