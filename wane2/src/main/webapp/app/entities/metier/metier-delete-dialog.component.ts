import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMetier } from 'app/shared/model/metier.model';
import { MetierService } from './metier.service';

@Component({
    selector: 'jhi-metier-delete-dialog',
    templateUrl: './metier-delete-dialog.component.html'
})
export class MetierDeleteDialogComponent {
    metier: IMetier;

    constructor(protected metierService: MetierService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.metierService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'metierListModification',
                content: 'Deleted an metier'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-metier-delete-popup',
    template: ''
})
export class MetierDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ metier }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MetierDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.metier = metier;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/metier', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/metier', { outlets: { popup: null } }]);
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
