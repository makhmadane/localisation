import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { IBonLivraison } from 'app/shared/model/bon-livraison.model';
import { BonLivraisonService } from './bon-livraison.service';

@Component({
    selector: 'jhi-bon-livraison-update',
    templateUrl: './bon-livraison-update.component.html'
})
export class BonLivraisonUpdateComponent implements OnInit {
    bonLivraison: IBonLivraison;
    isSaving: boolean;
    dateBlDp: any;

    constructor(protected bonLivraisonService: BonLivraisonService, protected activatedRoute: ActivatedRoute) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ bonLivraison }) => {
            this.bonLivraison = bonLivraison;
        });
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.bonLivraison.id !== undefined) {
            this.subscribeToSaveResponse(this.bonLivraisonService.update(this.bonLivraison));
        } else {
            this.subscribeToSaveResponse(this.bonLivraisonService.create(this.bonLivraison));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBonLivraison>>) {
        result.subscribe((res: HttpResponse<IBonLivraison>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }
}
