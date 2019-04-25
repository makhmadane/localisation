import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IProspection } from 'app/shared/model/prospection.model';
import { ProspectionService } from './prospection.service';

@Component({
    selector: 'jhi-prospection-delete-dialog',
    templateUrl: './prospection-delete-dialog.component.html'
})
export class ProspectionDeleteDialogComponent {
    prospection: IProspection;

    constructor(
        protected prospectionService: ProspectionService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.prospectionService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'prospectionListModification',
                content: 'Deleted an prospection'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-prospection-delete-popup',
    template: ''
})
export class ProspectionDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ prospection }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ProspectionDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.prospection = prospection;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/prospection', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/prospection', { outlets: { popup: null } }]);
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
