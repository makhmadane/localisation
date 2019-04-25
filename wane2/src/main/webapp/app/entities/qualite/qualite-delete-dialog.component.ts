import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IQualite } from 'app/shared/model/qualite.model';
import { QualiteService } from './qualite.service';

@Component({
    selector: 'jhi-qualite-delete-dialog',
    templateUrl: './qualite-delete-dialog.component.html'
})
export class QualiteDeleteDialogComponent {
    qualite: IQualite;

    constructor(protected qualiteService: QualiteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.qualiteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'qualiteListModification',
                content: 'Deleted an qualite'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-qualite-delete-popup',
    template: ''
})
export class QualiteDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ qualite }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(QualiteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.qualite = qualite;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/qualite', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/qualite', { outlets: { popup: null } }]);
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
