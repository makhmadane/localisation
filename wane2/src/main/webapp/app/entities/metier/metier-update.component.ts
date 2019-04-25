import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { IMetier } from 'app/shared/model/metier.model';
import { MetierService } from './metier.service';

@Component({
    selector: 'jhi-metier-update',
    templateUrl: './metier-update.component.html'
})
export class MetierUpdateComponent implements OnInit {
    metier: IMetier;
    isSaving: boolean;

    constructor(protected metierService: MetierService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ metier }) => {
            this.metier = metier;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.metier.id !== undefined) {
            this.subscribeToSaveResponse(this.metierService.update(this.metier));
        } else {
            this.subscribeToSaveResponse(this.metierService.create(this.metier));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMetier>>) {
        result.subscribe((res: HttpResponse<IMetier>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
