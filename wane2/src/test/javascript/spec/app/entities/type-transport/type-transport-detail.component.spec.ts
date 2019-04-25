/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { TypeTransportDetailComponent } from 'app/entities/type-transport/type-transport-detail.component';
import { TypeTransport } from 'app/shared/model/type-transport.model';

describe('Component Tests', () => {
    describe('TypeTransport Management Detail Component', () => {
        let comp: TypeTransportDetailComponent;
        let fixture: ComponentFixture<TypeTransportDetailComponent>;
        const route = ({ data: of({ typeTransport: new TypeTransport(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [TypeTransportDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TypeTransportDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeTransportDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.typeTransport).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
