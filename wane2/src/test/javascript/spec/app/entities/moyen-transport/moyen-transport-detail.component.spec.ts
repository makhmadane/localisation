/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { MoyenTransportDetailComponent } from 'app/entities/moyen-transport/moyen-transport-detail.component';
import { MoyenTransport } from 'app/shared/model/moyen-transport.model';

describe('Component Tests', () => {
    describe('MoyenTransport Management Detail Component', () => {
        let comp: MoyenTransportDetailComponent;
        let fixture: ComponentFixture<MoyenTransportDetailComponent>;
        const route = ({ data: of({ moyenTransport: new MoyenTransport(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [MoyenTransportDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(MoyenTransportDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(MoyenTransportDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.moyenTransport).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
