/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YesColaTestModule } from '../../../test.module';
import { StockInitialDeleteDialogComponent } from 'app/entities/stock-initial/stock-initial-delete-dialog.component';
import { StockInitialService } from 'app/entities/stock-initial/stock-initial.service';

describe('Component Tests', () => {
    describe('StockInitial Management Delete Component', () => {
        let comp: StockInitialDeleteDialogComponent;
        let fixture: ComponentFixture<StockInitialDeleteDialogComponent>;
        let service: StockInitialService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [StockInitialDeleteDialogComponent]
            })
                .overrideTemplate(StockInitialDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(StockInitialDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(StockInitialService);
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
