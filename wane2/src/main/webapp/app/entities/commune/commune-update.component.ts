import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ICommune } from 'app/shared/model/commune.model';
import { CommuneService } from './commune.service';
import { ISecteur } from 'app/shared/model/secteur.model';
import { SecteurService } from 'app/entities/secteur';

@Component({
    selector: 'jhi-commune-update',
    templateUrl: './commune-update.component.html'
})
export class CommuneUpdateComponent implements OnInit {
    commune: ICommune;
    isSaving: boolean;

    secteurs: ISecteur[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected communeService: CommuneService,
        protected secteurService: SecteurService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commune }) => {
            this.commune = commune;
        });
        this.secteurService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISecteur[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISecteur[]>) => response.body)
            )
            .subscribe((res: ISecteur[]) => (this.secteurs = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.commune.id !== undefined) {
            this.subscribeToSaveResponse(this.communeService.update(this.commune));
        } else {
            this.subscribeToSaveResponse(this.communeService.create(this.commune));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommune>>) {
        result.subscribe((res: HttpResponse<ICommune>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackSecteurById(index: number, item: ISecteur) {
        return item.id;
    }

    getSelected(selectedVals: Array<any>, option: any) {
        if (selectedVals) {
            for (let i = 0; i < selectedVals.length; i++) {
                if (option.id === selectedVals[i].id) {
                    return selectedVals[i];
                }
            }
        }
        return option;
    }
}
