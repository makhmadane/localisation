/* tslint:disable max-line-length */
import { ComponentFixture, TestBed } from '@angular/core/testing';
import { Observable, of } from 'rxjs';
import { HttpHeaders, HttpResponse } from '@angular/common/http';

import { YesColaTestModule } from '../../../test.module';
import { TabletteComponent } from 'app/entities/tablette/tablette.component';
import { TabletteService } from 'app/entities/tablette/tablette.service';
import { Tablette } from 'app/shared/model/tablette.model';

describe('Component Tests', () => {
    describe('Tablette Management Component', () => {
        let comp: TabletteComponent;
        let fixture: ComponentFixture<TabletteComponent>;
        let service: TabletteService;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [TabletteComponent],
                providers: []
            })
                .overrideTemplate(TabletteComponent, '')
                .compileComponents();

            fixture = TestBed.createComponent(TabletteComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TabletteService);
        });

        it('Should call load all on init', () => {
            // GIVEN
            const headers = new HttpHeaders().append('link', 'link;link');
            spyOn(service, 'query').and.returnValue(
                of(
                    new HttpResponse({
                        body: [new Tablette(123)],
                        headers
                    })
                )
            );

            // WHEN
            comp.ngOnInit();

            // THEN
            expect(service.query).toHaveBeenCalled();
            expect(comp.tablettes[0]).toEqual(jasmine.objectContaining({ id: 123 }));
        });
    });
});
