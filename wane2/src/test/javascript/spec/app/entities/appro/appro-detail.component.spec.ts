/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { ApproDetailComponent } from 'app/entities/appro/appro-detail.component';
import { Appro } from 'app/shared/model/appro.model';

describe('Component Tests', () => {
    describe('Appro Management Detail Component', () => {
        let comp: ApproDetailComponent;
        let fixture: ComponentFixture<ApproDetailComponent>;
        const route = ({ data: of({ appro: new Appro(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [ApproDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(ApproDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(ApproDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.appro).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
