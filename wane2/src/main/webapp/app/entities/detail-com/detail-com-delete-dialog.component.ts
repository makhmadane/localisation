import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetailCom } from 'app/shared/model/detail-com.model';
import { DetailComService } from './detail-com.service';

@Component({
    selector: 'jhi-detail-com-delete-dialog',
    templateUrl: './detail-com-delete-dialog.component.html'
})
export class DetailComDeleteDialogComponent {
    detailCom: IDetailCom;

    constructor(
        protected detailComService: DetailComService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.detailComService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'detailComListModification',
                content: 'Deleted an detailCom'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-detail-com-delete-popup',
    template: ''
})
export class DetailComDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ detailCom }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DetailComDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.detailCom = detailCom;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/detail-com', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/detail-com', { outlets: { popup: null } }]);
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
