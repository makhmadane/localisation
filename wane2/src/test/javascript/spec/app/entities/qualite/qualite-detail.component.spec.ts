/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { QualiteDetailComponent } from 'app/entities/qualite/qualite-detail.component';
import { Qualite } from 'app/shared/model/qualite.model';

describe('Component Tests', () => {
    describe('Qualite Management Detail Component', () => {
        let comp: QualiteDetailComponent;
        let fixture: ComponentFixture<QualiteDetailComponent>;
        const route = ({ data: of({ qualite: new Qualite(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [QualiteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(QualiteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QualiteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.qualite).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
