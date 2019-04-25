/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YesColaTestModule } from '../../../test.module';
import { DepotDeleteDialogComponent } from 'app/entities/depot/depot-delete-dialog.component';
import { DepotService } from 'app/entities/depot/depot.service';

describe('Component Tests', () => {
    describe('Depot Management Delete Component', () => {
        let comp: DepotDeleteDialogComponent;
        let fixture: ComponentFixture<DepotDeleteDialogComponent>;
        let service: DepotService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [DepotDeleteDialogComponent]
            })
                .overrideTemplate(DepotDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DepotDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DepotService);
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
