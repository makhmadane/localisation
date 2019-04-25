/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YesColaTestModule } from '../../../test.module';
import { TypeRglmentDeleteDialogComponent } from 'app/entities/type-rglment/type-rglment-delete-dialog.component';
import { TypeRglmentService } from 'app/entities/type-rglment/type-rglment.service';

describe('Component Tests', () => {
    describe('TypeRglment Management Delete Component', () => {
        let comp: TypeRglmentDeleteDialogComponent;
        let fixture: ComponentFixture<TypeRglmentDeleteDialogComponent>;
        let service: TypeRglmentService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [TypeRglmentDeleteDialogComponent]
            })
                .overrideTemplate(TypeRglmentDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(TypeRglmentDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(TypeRglmentService);
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
