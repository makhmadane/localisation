/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { ParfumDetailComponent } from 'app/entities/parfum/parfum-detail.component';
import { Parfum } from 'app/shared/model/parfum.model';

describe('Component Tests', () => {
    describe('Parfum Management Detail Component', () => {
        let comp: ParfumDetailComponent;
        let fixture: ComponentFixture<ParfumDetailComponent>;
        const route = ({ data: of({ parfum: new Parfum(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [ParfumDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ParfumDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ParfumDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.parfum).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
