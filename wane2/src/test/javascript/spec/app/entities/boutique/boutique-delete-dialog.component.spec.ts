/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YesColaTestModule } from '../../../test.module';
import { BoutiqueDeleteDialogComponent } from 'app/entities/boutique/boutique-delete-dialog.component';
import { BoutiqueService } from 'app/entities/boutique/boutique.service';

describe('Component Tests', () => {
    describe('Boutique Management Delete Component', () => {
        let comp: BoutiqueDeleteDialogComponent;
        let fixture: ComponentFixture<BoutiqueDeleteDialogComponent>;
        let service: BoutiqueService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [BoutiqueDeleteDialogComponent]
            })
                .overrideTemplate(BoutiqueDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BoutiqueDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BoutiqueService);
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
