/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YesColaTestModule } from '../../../test.module';
import { TypeTransportDeleteDialogComponent } from 'app/entities/type-transport/type-transport-delete-dialog.component';
import { TypeTransportService } from 'app/entities/type-transport/type-transport.service';

describe('Component Tests', () => {
    describe('TypeTransport Management Delete Component', () => {
        let comp: TypeTransportDeleteDialogComponent;
        let fixture: ComponentFixture<TypeTransportDeleteDialogComponent>;
        let service: TypeTransportService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [TypeTransportDeleteDialogComponent]
            })
                .overrideTemplate(TypeTransportDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeTransportDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeTransportService);
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
