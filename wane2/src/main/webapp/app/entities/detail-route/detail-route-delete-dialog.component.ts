import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IDetailRoute } from 'app/shared/model/detail-route.model';
import { DetailRouteService } from './detail-route.service';

@Component({
    selector: 'jhi-detail-route-delete-dialog',
    templateUrl: './detail-route-delete-dialog.component.html'
})
export class DetailRouteDeleteDialogComponent {
    detailRoute: IDetailRoute;

    constructor(
        protected detailRouteService: DetailRouteService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.detailRouteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'detailRouteListModification',
                content: 'Deleted an detailRoute'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-detail-route-delete-popup',
    template: ''
})
export class DetailRouteDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ detailRoute }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(DetailRouteDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.detailRoute = detailRoute;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/detail-route', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/detail-route', { outlets: { popup: null } }]);
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
