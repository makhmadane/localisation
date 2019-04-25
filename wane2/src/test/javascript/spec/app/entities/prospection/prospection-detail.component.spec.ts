/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { ProspectionDetailComponent } from 'app/entities/prospection/prospection-detail.component';
import { Prospection } from 'app/shared/model/prospection.model';

describe('Component Tests', () => {
    describe('Prospection Management Detail Component', () => {
        let comp: ProspectionDetailComponent;
        let fixture: ComponentFixture<ProspectionDetailComponent>;
        const route = ({ data: of({ prospection: new Prospection(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [ProspectionDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ProspectionDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ProspectionDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.prospection).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
