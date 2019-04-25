/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YesColaTestModule } from '../../../test.module';
import { DetailComDeleteDialogComponent } from 'app/entities/detail-com/detail-com-delete-dialog.component';
import { DetailComService } from 'app/entities/detail-com/detail-com.service';

describe('Component Tests', () => {
    describe('DetailCom Management Delete Component', () => {
        let comp: DetailComDeleteDialogComponent;
        let fixture: ComponentFixture<DetailComDeleteDialogComponent>;
        let service: DetailComService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [DetailComDeleteDialogComponent]
            })
                .overrideTemplate(DetailComDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(DetailComDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(DetailComService);
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
