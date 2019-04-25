import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITypeRglment } from 'app/shared/model/type-rglment.model';
import { TypeRglmentService } from './type-rglment.service';

@Component({
    selector: 'jhi-type-rglment-update',
    templateUrl: './type-rglment-update.component.html'
})
export class TypeRglmentUpdateComponent implements OnInit {
    typeRglment: ITypeRglment;
    isSaving: boolean;

    constructor(protected typeRglmentService: TypeRglmentService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeRglment }) => {
            this.typeRglment = typeRglment;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeRglment.id !== undefined) {
            this.subscribeToSaveResponse(this.typeRglmentService.update(this.typeRglment));
        } else {
            this.subscribeToSaveResponse(this.typeRglmentService.create(this.typeRglment));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeRglment>>) {
        result.subscribe((res: HttpResponse<ITypeRglment>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
