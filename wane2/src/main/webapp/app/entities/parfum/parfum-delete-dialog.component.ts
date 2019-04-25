import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IParfum } from 'app/shared/model/parfum.model';
import { ParfumService } from './parfum.service';

@Component({
    selector: 'jhi-parfum-delete-dialog',
    templateUrl: './parfum-delete-dialog.component.html'
})
export class ParfumDeleteDialogComponent {
    parfum: IParfum;

    constructor(protected parfumService: ParfumService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.parfumService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'parfumListModification',
                content: 'Deleted an parfum'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-parfum-delete-popup',
    template: ''
})
export class ParfumDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ parfum }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ParfumDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.parfum = parfum;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/parfum', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/parfum', { outlets: { popup: null } }]);
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
