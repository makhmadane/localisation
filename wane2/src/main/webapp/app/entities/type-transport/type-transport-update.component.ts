import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { ITypeTransport } from 'app/shared/model/type-transport.model';
import { TypeTransportService } from './type-transport.service';

@Component({
    selector: 'jhi-type-transport-update',
    templateUrl: './type-transport-update.component.html'
})
export class TypeTransportUpdateComponent implements OnInit {
    typeTransport: ITypeTransport;
    isSaving: boolean;

    constructor(protected typeTransportService: TypeTransportService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ typeTransport }) => {
            this.typeTransport = typeTransport;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.typeTransport.id !== undefined) {
            this.subscribeToSaveResponse(this.typeTransportService.update(this.typeTransport));
        } else {
            this.subscribeToSaveResponse(this.typeTransportService.create(this.typeTransport));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITypeTransport>>) {
        result.subscribe((res: HttpResponse<ITypeTransport>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
