import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IParfum } from 'app/shared/model/parfum.model';
import { ParfumService } from './parfum.service';

@Component({
    selector: 'jhi-parfum-update',
    templateUrl: './parfum-update.component.html'
})
export class ParfumUpdateComponent implements OnInit {
    parfum: IParfum;
    isSaving: boolean;

    constructor(protected parfumService: ParfumService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ parfum }) => {
            this.parfum = parfum;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.parfum.id !== undefined) {
            this.subscribeToSaveResponse(this.parfumService.update(this.parfum));
        } else {
            this.subscribeToSaveResponse(this.parfumService.create(this.parfum));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IParfum>>) {
        result.subscribe((res: HttpResponse<IParfum>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
