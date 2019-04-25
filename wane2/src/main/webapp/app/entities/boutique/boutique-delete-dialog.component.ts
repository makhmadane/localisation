import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBoutique } from 'app/shared/model/boutique.model';
import { BoutiqueService } from './boutique.service';

@Component({
    selector: 'jhi-boutique-delete-dialog',
    templateUrl: './boutique-delete-dialog.component.html'
})
export class BoutiqueDeleteDialogComponent {
    boutique: IBoutique;

    constructor(protected boutiqueService: BoutiqueService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.boutiqueService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'boutiqueListModification',
                content: 'Deleted an boutique'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-boutique-delete-popup',
    template: ''
})
export class BoutiqueDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ boutique }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BoutiqueDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.boutique = boutique;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/boutique', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/boutique', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    }
                );
            }, 0);
        });
    }

    ngOnDestroy() {
        this.ngbModalRef = null;
    }
}
