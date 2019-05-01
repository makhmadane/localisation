import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ICommande } from 'app/shared/model/commande.model';
import { CommandeService } from './commande.service';

@Component({
    selector: 'jhi-commande-delete-dialog',
    templateUrl: './commande-delete-dialog.component.html'
})
export class CommandeDeleteDialogComponent {
    commande: ICommande;

    constructor(protected commandeService: CommandeService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.commandeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'commandeListModification',
                content: 'Deleted an commande'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-commande-delete-popup',
    template: ''
})
export class CommandeDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ commande }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(CommandeDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.commande = commande;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/commande', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/commande', { outlets: { popup: null } }]);
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
