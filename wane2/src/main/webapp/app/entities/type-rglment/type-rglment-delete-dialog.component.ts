import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITypeRglment } from 'app/shared/model/type-rglment.model';
import { TypeRglmentService } from './type-rglment.service';

@Component({
    selector: 'jhi-type-rglment-delete-dialog',
    templateUrl: './type-rglment-delete-dialog.component.html'
})
export class TypeRglmentDeleteDialogComponent {
    typeRglment: ITypeRglment;

    constructor(
        protected typeRglmentService: TypeRglmentService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.typeRglmentService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'typeRglmentListModification',
                content: 'Deleted an typeRglment'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-type-rglment-delete-popup',
    template: ''
})
export class TypeRglmentDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ typeRglment }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TypeRglmentDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.typeRglment = typeRglment;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/type-rglment', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/type-rglment', { outlets: { popup: null } }]);
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
