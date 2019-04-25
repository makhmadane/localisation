import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IStockInitial } from 'app/shared/model/stock-initial.model';
import { StockInitialService } from './stock-initial.service';

@Component({
    selector: 'jhi-stock-initial-delete-dialog',
    templateUrl: './stock-initial-delete-dialog.component.html'
})
export class StockInitialDeleteDialogComponent {
    stockInitial: IStockInitial;

    constructor(
        protected stockInitialService: StockInitialService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.stockInitialService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'stockInitialListModification',
                content: 'Deleted an stockInitial'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-stock-initial-delete-popup',
    template: ''
})
export class StockInitialDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ stockInitial }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(StockInitialDeleteDialogComponent as Component, {
                    size: 'lg',
                    backdrop: 'static'
                });
                this.ngbModalRef.componentInstance.stockInitial = stockInitial;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/stock-initial', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/stock-initial', { outlets: { popup: null } }]);
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
