import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDepot } from 'app/shared/model/depot.model';
import { DepotService } from './depot.service';

@Component({
    selector: 'jhi-depot-delete-dialog',
    templateUrl: './depot-delete-dialog.component.html'
})
export class DepotDeleteDialogComponent {
    depot: IDepot;

    constructor(protected depotService: DepotService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.depotService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'depotListModification',
                content: 'Deleted an depot'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-depot-delete-popup',
    template: ''
})
export class DepotDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ depot }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DepotDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.depot = depot;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/depot', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/depot', { outlets: { popup: null } }]);
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
