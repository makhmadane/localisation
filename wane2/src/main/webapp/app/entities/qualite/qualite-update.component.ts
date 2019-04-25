import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IQualite } from 'app/shared/model/qualite.model';
import { QualiteService } from './qualite.service';

@Component({
    selector: 'jhi-qualite-update',
    templateUrl: './qualite-update.component.html'
})
export class QualiteUpdateComponent implements OnInit {
    qualite: IQualite;
    isSaving: boolean;

    constructor(protected qualiteService: QualiteService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ qualite }) => {
            this.qualite = qualite;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.qualite.id !== undefined) {
            this.subscribeToSaveResponse(this.qualiteService.update(this.qualite));
        } else {
            this.subscribeToSaveResponse(this.qualiteService.create(this.qualite));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IQualite>>) {
        result.subscribe((res: HttpResponse<IQualite>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
