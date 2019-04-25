import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IBoutique } from 'app/shared/model/boutique.model';
import { BoutiqueService } from './boutique.service';
import { IMetier } from 'app/shared/model/metier.model';
import { MetierService } from 'app/entities/metier';
import { IQualite } from 'app/shared/model/qualite.model';
import { QualiteService } from 'app/entities/qualite';
import { ISecteur } from 'app/shared/model/secteur.model';
import { SecteurService } from 'app/entities/secteur';
import { IRoute } from 'app/shared/model/route.model';
import { RouteService } from 'app/entities/route';
import { ICommande } from 'app/shared/model/commande.model';
import { CommandeService } from 'app/entities/commande';
import { IProspection } from 'app/shared/model/prospection.model';
import { ProspectionService } from 'app/entities/prospection';

@Component({
    selector: 'jhi-boutique-update',
    templateUrl: './boutique-update.component.html'
})
export class BoutiqueUpdateComponent implements OnInit {
    boutique: IBoutique;
    isSaving: boolean;

    metiers: IMetier[];

    qualites: IQualite[];

    secteurs: ISecteur[];

    routes: IRoute[];

    commandes: ICommande[];

    prospections: IProspection[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected boutiqueService: BoutiqueService,
        protected metierService: MetierService,
        protected qualiteService: QualiteService,
        protected secteurService: SecteurService,
        protected routeService: RouteService,
        protected commandeService: CommandeService,
        protected prospectionService: ProspectionService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ boutique }) => {
            this.boutique = boutique;
        });
        this.metierService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMetier[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMetier[]>) => response.body)
            )
            .subscribe((res: IMetier[]) => (this.metiers = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.qualiteService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IQualite[]>) => mayBeOk.ok),
                map((response: HttpResponse<IQualite[]>) => response.body)
            )
            .subscribe((res: IQualite[]) => (this.qualites = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.secteurService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISecteur[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISecteur[]>) => response.body)
            )
            .subscribe((res: ISecteur[]) => (this.secteurs = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.routeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IRoute[]>) => mayBeOk.ok),
                map((response: HttpResponse<IRoute[]>) => response.body)
            )
            .subscribe((res: IRoute[]) => (this.routes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.commandeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommande[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommande[]>) => response.body)
            )
            .subscribe((res: ICommande[]) => (this.commandes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.prospectionService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IProspection[]>) => mayBeOk.ok),
                map((response: HttpResponse<IProspection[]>) => response.body)
            )
            .subscribe((res: IProspection[]) => (this.prospections = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.boutique.id !== undefined) {
            this.subscribeToSaveResponse(this.boutiqueService.update(this.boutique));
        } else {
            this.subscribeToSaveResponse(this.boutiqueService.create(this.boutique));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IBoutique>>) {
        result.subscribe((res: HttpResponse<IBoutique>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMetierById(index: number, item: IMetier) {
        return item.id;
    }

    trackQualiteById(index: number, item: IQualite) {
        return item.id;
    }

    trackSecteurById(index: number, item: ISecteur) {
        return item.id;
    }

    trackRouteById(index: number, item: IRoute) {
        return item.id;
    }

    trackCommandeById(index: number, item: ICommande) {
        return item.id;
    }

    trackProspectionById(index: number, item: IProspection) {
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
