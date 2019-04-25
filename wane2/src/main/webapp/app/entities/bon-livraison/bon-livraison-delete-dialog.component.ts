import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IBonLivraison } from 'app/shared/model/bon-livraison.model';
import { BonLivraisonService } from './bon-livraison.service';

@Component({
    selector: 'jhi-bon-livraison-delete-dialog',
    templateUrl: './bon-livraison-delete-dialog.component.html'
})
export class BonLivraisonDeleteDialogComponent {
    bonLivraison: IBonLivraison;

    constructor(
        protected bonLivraisonService: BonLivraisonService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.bonLivraisonService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'bonLivraisonListModification',
                content: 'Deleted an bonLivraison'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-bon-livraison-delete-popup',
    template: ''
})
export class BonLivraisonDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ bonLivraison }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(BonLivraisonDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.bonLivraison = bonLivraison;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/bon-livraison', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/bon-livraison', { outlets: { popup: null } }]);
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
