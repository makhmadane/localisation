import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { ISecteur } from 'app/shared/model/secteur.model';
import { SecteurService } from './secteur.service';
import { IRoute } from 'app/shared/model/route.model';
import { RouteService } from 'app/entities/route';
import { ICommune } from 'app/shared/model/commune.model';
import { CommuneService } from 'app/entities/commune';

@Component({
    selector: 'jhi-secteur-update',
    templateUrl: './secteur-update.component.html'
})
export class SecteurUpdateComponent implements OnInit {
    secteur: ISecteur;
    isSaving: boolean;

    routes: IRoute[];

    communes: ICommune[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected secteurService: SecteurService,
        protected routeService: RouteService,
        protected communeService: CommuneService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ secteur }) => {
            this.secteur = secteur;
        });
        this.routeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRoute[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRoute[]>) => response.body)
            )
            .subscribe((res: IRoute[]) => (this.routes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.communeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommune[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommune[]>) => response.body)
            )
            .subscribe((res: ICommune[]) => (this.communes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.secteur.id !== undefined) {
            this.subscribeToSaveResponse(this.secteurService.update(this.secteur));
        } else {
            this.subscribeToSaveResponse(this.secteurService.create(this.secteur));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ISecteur>>) {
        result.subscribe((res: HttpResponse<ISecteur>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackRouteById(index: number, item: IRoute) {
        return item.id;
    }

    trackCommuneById(index: number, item: ICommune) {
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
