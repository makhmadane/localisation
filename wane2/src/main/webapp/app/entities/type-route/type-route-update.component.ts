import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITypeRoute } from 'app/shared/model/type-route.model';
import { TypeRouteService } from './type-route.service';

@Component({
    selector: 'jhi-type-route-update',
    templateUrl: './type-route-update.component.html'
})
export class TypeRouteUpdateComponent implements OnInit {
    typeRoute: ITypeRoute;
    isSaving: boolean;

    constructor(protected typeRouteService: TypeRouteService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeRoute }) => {
            this.typeRoute = typeRoute;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeRoute.id !== undefined) {
            this.subscribeToSaveResponse(this.typeRouteService.update(this.typeRoute));
        } else {
            this.subscribeToSaveResponse(this.typeRouteService.create(this.typeRoute));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeRoute>>) {
        result.subscribe((res: HttpResponse<ITypeRoute>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
