/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YesColaTestModule } from '../../../test.module';
import { QualiteDeleteDialogComponent } from 'app/entities/qualite/qualite-delete-dialog.component';
import { QualiteService } from 'app/entities/qualite/qualite.service';

describe('Component Tests', () => {
    describe('Qualite Management Delete Component', () => {
        let comp: QualiteDeleteDialogComponent;
        let fixture: ComponentFixture<QualiteDeleteDialogComponent>;
        let service: QualiteService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [QualiteDeleteDialogComponent]
            })
                .overrideTemplate(QualiteDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(QualiteDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(QualiteService);
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
