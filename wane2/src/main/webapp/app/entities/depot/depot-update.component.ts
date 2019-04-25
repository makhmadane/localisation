import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IDepot } from 'app/shared/model/depot.model';
import { DepotService } from './depot.service';

@Component({
    selector: 'jhi-depot-update',
    templateUrl: './depot-update.component.html'
})
export class DepotUpdateComponent implements OnInit {
    depot: IDepot;
    isSaving: boolean;

    constructor(protected depotService: DepotService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ depot }) => {
            this.depot = depot;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.depot.id !== undefined) {
            this.subscribeToSaveResponse(this.depotService.update(this.depot));
        } else {
            this.subscribeToSaveResponse(this.depotService.create(this.depot));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDepot>>) {
        result.subscribe((res: HttpResponse<IDepot>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
