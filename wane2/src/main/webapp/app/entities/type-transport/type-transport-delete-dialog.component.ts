import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeTransport } from 'app/shared/model/type-transport.model';
import { TypeTransportService } from './type-transport.service';

@Component({
    selector: 'jhi-type-transport-delete-dialog',
    templateUrl: './type-transport-delete-dialog.component.html'
})
export class TypeTransportDeleteDialogComponent {
    typeTransport: ITypeTransport;

    constructor(
        protected typeTransportService: TypeTransportService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeTransportService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeTransportListModification',
                content: 'Deleted an typeTransport'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-transport-delete-popup',
    template: ''
})
export class TypeTransportDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeTransport }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeTransportDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeTransport = typeTransport;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-transport', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-transport', { outlets: { popup: null } }]);
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
