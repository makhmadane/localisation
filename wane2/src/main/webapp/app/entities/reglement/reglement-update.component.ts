import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IReglement } from 'app/shared/model/reglement.model';
import { ReglementService } from './reglement.service';
import { ITypeRglment } from 'app/shared/model/type-rglment.model';
import { TypeRglmentService } from 'app/entities/type-rglment';
import { ICommande } from 'app/shared/model/commande.model';
import { CommandeService } from 'app/entities/commande';

@Component({
    selector: 'jhi-reglement-update',
    templateUrl: './reglement-update.component.html'
})
export class ReglementUpdateComponent implements OnInit {
    reglement: IReglement;
    isSaving: boolean;

    typerglments: ITypeRglment[];

    commandes: ICommande[];
    dateRegDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected reglementService: ReglementService,
        protected typeRglmentService: TypeRglmentService,
        protected commandeService: CommandeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ reglement }) => {
            this.reglement = reglement;
        });
        this.typeRglmentService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeRglment[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeRglment[]>) => response.body)
            )
            .subscribe((res: ITypeRglment[]) => (this.typerglments = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.commandeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommande[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommande[]>) => response.body)
            )
            .subscribe((res: ICommande[]) => (this.commandes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.reglement.id !== undefined) {
            this.subscribeToSaveResponse(this.reglementService.update(this.reglement));
        } else {
            this.subscribeToSaveResponse(this.reglementService.create(this.reglement));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IReglement>>) {
        result.subscribe((res: HttpResponse<IReglement>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackTypeRglmentById(index: number, item: ITypeRglment) {
        return item.id;
    }

    trackCommandeById(index: number, item: ICommande) {
        return item.id;
    }
}
