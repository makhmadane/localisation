import { Component, OnInit, OnDestroy } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';

import { NgbActiveModal, NgbModal, NgbModalRef } from '@ng-bootstrap/ng-bootstrap';
import { JhiEventManager } from 'ng-jhipster';

import { IReglement } from 'app/shared/model/reglement.model';
import { ReglementService } from './reglement.service';

@Component({
    selector: 'jhi-reglement-delete-dialog',
    templateUrl: './reglement-delete-dialog.component.html'
})
export class ReglementDeleteDialogComponent {
    reglement: IReglement;

    constructor(
        protected reglementService: ReglementService,
        public activeModal: NgbActiveModal,
        protected eventManager: JhiEventManager
    ) {}

    clear() {
        this.activeModal.dismiss('cancel');
    }

    confirmDelete(id: number) {
        this.reglementService.delete(id).subscribe(response => {
            this.eventManager.broadcast({
                name: 'reglementListModification',
                content: 'Deleted an reglement'
            });
            this.activeModal.dismiss(true);
        });
    }
}

@Component({
    selector: 'jhi-reglement-delete-popup',
    template: ''
})
export class ReglementDeletePopupComponent implements OnInit, OnDestroy {
    protected ngbModalRef: NgbModalRef;

    constructor(protected activatedRoute: ActivatedRoute, protected router: Router, protected modalService: NgbModal) {}

    ngOnInit() {
        this.activatedRoute.data.subscribe(({ reglement }) => {
            setTimeout(() => {
                this.ngbModalRef = this.modalService.open(ReglementDeleteDialogComponent as Component, { size: 'lg', backdrop: 'static' });
                this.ngbModalRef.componentInstance.reglement = reglement;
                this.ngbModalRef.result.then(
                    result => {
                        this.router.navigate(['/reglement', { outlets: { popup: null } }]);
                        this.ngbModalRef = null;
                    },
                    reason => {
                        this.router.navigate(['/reglement', { outlets: { popup: null } }]);
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
