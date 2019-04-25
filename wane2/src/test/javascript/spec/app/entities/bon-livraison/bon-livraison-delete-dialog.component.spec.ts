/* tslint:disable max-line-length */
import { ComponentFixture, TestBed, inject, fakeAsync, tick } from '@angular/core/testing';
import { NgbActiveModal } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { JhiEventManager } from 'ng-jhipster';

import { YesColaTestModule } from '../../../test.module';
import { BonLivraisonDeleteDialogComponent } from 'app/entities/bon-livraison/bon-livraison-delete-dialog.component';
import { BonLivraisonService } from 'app/entities/bon-livraison/bon-livraison.service';

describe('Component Tests', () => {
    describe('BonLivraison Management Delete Component', () => {
        let comp: BonLivraisonDeleteDialogComponent;
        let fixture: ComponentFixture<BonLivraisonDeleteDialogComponent>;
        let service: BonLivraisonService;
        let mockEventManager: any;
        let mockActiveModal: any;

        beforeEach(() => {
            TestBed.configureTestingModule({
                imports: [YesColaTestModule],
                declarations: [BonLivraisonDeleteDialogComponent]
            })
                .overrideTemplate(BonLivraisonDeleteDialogComponent, '')
                .compileComponents();
            fixture = TestBed.createComponent(BonLivraisonDeleteDialogComponent);
            comp = fixture.componentInstance;
            service = fixture.debugElement.injector.get(BonLivraisonService);
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
