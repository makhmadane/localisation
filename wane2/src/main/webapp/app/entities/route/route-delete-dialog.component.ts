import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IRoute } from 'app/shared/model/route.model';
import { RouteService } from './route.service';

@Component({
    selector: 'jhi-route-delete-dialog',
    templateUrl: './route-delete-dialog.component.html'
})
export class RouteDeleteDialogComponent {
    route: IRoute;

    constructor(protected routeService: RouteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.routeService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'routeListModification',
                content: 'Deleted an route'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-route-delete-popup',
    template: ''
})
export class RouteDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ route }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(RouteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.route = route;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/route', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/route', { outlets: { popup: null } }]);
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
