import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { ITablette } from 'app/shared/model/tablette.model';
import { TabletteService } from './tablette.service';

@Component({
    selector: 'jhi-tablette-delete-dialog',
    templateUrl: './tablette-delete-dialog.component.html'
})
export class TabletteDeleteDialogComponent {
    tablette: ITablette;

    constructor(protected tabletteService: TabletteService, public activeModal: NgbActiveModal, protected eventManager: JhiEventManager) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.tabletteService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'tabletteListModification',
                content: 'Deleted an tablette'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-tablette-delete-popup',
    template: ''
})
export class TabletteDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ tablette }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(TabletteDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.tablette = tablette;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/tablette', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/tablette', { outlets: { popup: null } }]);
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
