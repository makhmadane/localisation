import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDetailRoute } from 'app/shared/model/detail-route.model';
import { DetailRouteService } from './detail-route.service';
import { ICommande } from 'app/shared/model/commande.model';
import { CommandeService } from 'app/entities/commande';
import { IRoute } from 'app/shared/model/route.model';
import { RouteService } from 'app/entities/route';
import { IBoutique } from 'app/shared/model/boutique.model';
import { BoutiqueService } from 'app/entities/boutique';

@Component({
    selector: 'jhi-detail-route-update',
    templateUrl: './detail-route-update.component.html'
})
export class DetailRouteUpdateComponent implements OnInit {
    detailRoute: IDetailRoute;
    isSaving: boolean;

    commandes: ICommande[];

    routes: IRoute[];

    boutiques: IBoutique[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected detailRouteService: DetailRouteService,
        protected commandeService: CommandeService,
        protected routeService: RouteService,
        protected boutiqueService: BoutiqueService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ detailRoute }) => {
            this.detailRoute = detailRoute;
        });
        this.commandeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommande[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommande[]>) => response.body)
            )
            .subscribe((res: ICommande[]) => (this.commandes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.routeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRoute[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRoute[]>) => response.body)
            )
            .subscribe((res: IRoute[]) => (this.routes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.boutiqueService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBoutique[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBoutique[]>) => response.body)
            )
            .subscribe((res: IBoutique[]) => (this.boutiques = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.detailRoute.id !== undefined) {
            this.subscribeToSaveResponse(this.detailRouteService.update(this.detailRoute));
        } else {
            this.subscribeToSaveResponse(this.detailRouteService.create(this.detailRoute));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetailRoute>>) {
        result.subscribe((res: HttpResponse<IDetailRoute>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCommandeById(index: number, item: ICommande) {
        return item.id;
    }

    trackRouteById(index: number, item: IRoute) {
        return item.id;
    }

    trackBoutiqueById(index: number, item: IBoutique) {
        return item.id;
    }
}
