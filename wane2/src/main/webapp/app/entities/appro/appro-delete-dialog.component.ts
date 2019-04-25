import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IAppro } from 'app/shared/model/appro.model';
import { ApproService } from './appro.service';

@Component({
    selector: 'jhi-appro-delete-dialog',
    templateUrl: './appro-delete-dialog.component.html'
})
export class ApproDeleteDialogComponent {
    appro: IAppro;

    constructor(protected approService: ApproService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.approService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'approListModification',
                content: 'Deleted an appro'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-appro-delete-popup',
    template: ''
})
export class ApproDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ appro }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ApproDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.appro = appro;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/appro', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/appro', { outlets: { popup: null } }]);
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
