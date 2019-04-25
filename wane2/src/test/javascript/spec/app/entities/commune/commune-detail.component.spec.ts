/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { CommuneDetailComponent } from 'app/entities/commune/commune-detail.component';
import { Commune } from 'app/shared/model/commune.model';

describe('Component Tests', () => {
    describe('Commune Management Detail Component', () => {
        let comp: CommuneDetailComponent;
        let fixture: ComponentFixture<CommuneDetailComponent>;
        const route = ({ data: of({ commune: new Commune(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [CommuneDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(CommuneDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(CommuneDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.commune).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
