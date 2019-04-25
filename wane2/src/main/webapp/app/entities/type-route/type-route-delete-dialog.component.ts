import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeRoute } from 'app/shared/model/type-route.model';
import { TypeRouteService } from './type-route.service';

@Component({
    selector: 'jhi-type-route-delete-dialog',
    templateUrl: './type-route-delete-dialog.component.html'
})
export class TypeRouteDeleteDialogComponent {
    typeRoute: ITypeRoute;

    constructor(
        protected typeRouteService: TypeRouteService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeRouteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeRouteListModification',
                content: 'Deleted an typeRoute'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-route-delete-popup',
    template: ''
})
export class TypeRouteDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeRoute }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeRouteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.typeRoute = typeRoute;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-route', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-route', { outlets: { popup: null } }]);
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
