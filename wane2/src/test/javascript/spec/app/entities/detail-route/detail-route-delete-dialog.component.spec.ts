/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YesColaTestModule } from '../../../test.module';
import { DetailRouteDeleteDialogComponent } from 'app/entities/detail-route/detail-route-delete-dialog.component';
import { DetailRouteService } from 'app/entities/detail-route/detail-route.service';

describe('Component Tests', () => {
    describe('DetailRoute Management Delete Component', () => {
        let comp: DetailRouteDeleteDialogComponent;
        let fixture: ComponentFixture<DetailRouteDeleteDialogComponent>;
        let service: DetailRouteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [DetailRouteDeleteDialogComponent]
            })
                .overrideTemplate(DetailRouteDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DetailRouteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetailRouteService);
            mockEventManager = fixture.debugElement.injector.get(JhiEventManager);
            mockActiveModal = fixture.debugElement.injector.get(NgbActiveModal);
        });

        describe('confirmDelete', () => {
            it('Should call delete service on confirmDelete', inject(
                [],
                fakeAsync(() => {
                    // GIVEN
                    spyOn(service, 'delete').and.returnValue(of({}));

                    // WHEN
                    comp.confirmDelete(123);
                    tick();

                    // THEN
                    expect(service.delete).toHaveBeenCalledWith(123);
                    expect(mockActiveModal.dismissSpy).toHaveBeenCalled();
                    expect(mockEventManager.broadcastSpy).toHaveBeenCalled();
                })
            ));
        });
    });
});
