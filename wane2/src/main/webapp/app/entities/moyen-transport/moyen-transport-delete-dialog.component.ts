import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IMoyenTransport } from 'app/shared/model/moyen-transport.model';
import { MoyenTransportService } from './moyen-transport.service';

@Component({
    selector: 'jhi-moyen-transport-delete-dialog',
    templateUrl: './moyen-transport-delete-dialog.component.html'
})
export class MoyenTransportDeleteDialogComponent {
    moyenTransport: IMoyenTransport;

    constructor(
        protected moyenTransportService: MoyenTransportService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.moyenTransportService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'moyenTransportListModification',
                content: 'Deleted an moyenTransport'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-moyen-transport-delete-popup',
    template: ''
})
export class MoyenTransportDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ moyenTransport }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(MoyenTransportDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.moyenTransport = moyenTransport;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/moyen-transport', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/moyen-transport', { outlets: { popup: null } }]);
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
