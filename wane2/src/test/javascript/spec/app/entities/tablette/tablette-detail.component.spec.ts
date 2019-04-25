/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { ActivatedRoute } from '@angular/router';
import { of } from 'rxjs';

import { YesColaTestModule } from '../../../test.module';
import { TabletteDetailComponent } from 'app/entities/tablette/tablette-detail.component';
import { Tablette } from 'app/shared/model/tablette.model';

describe('Component Tests', () => {
    describe('Tablette Management Detail Component', () => {
        let comp: TabletteDetailComponent;
        let fixture: ComponentFixture<TabletteDetailComponent>;
        const route = ({ data: of({ tablette: new Tablette(123) }) } as any) as ActivatedRoute;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [TabletteDetailComponent],
                providers: [{ provide: ActivatedRoute, useValue: route }]
            })
                .overrideTemplate(TabletteDetailComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TabletteDetailComponent);
            comp = fixture.componentInstance;
        });

        describe('OnInit', () => {
            it('Should call load all on init', () => {
                // GIVEN

                // WHEN
                comp.ngOnInit();

                // THEN
                expect(comp.tablette).toEqual(jasmine.objectContaining({ id: 123 }));
            });
        });
    });
});
