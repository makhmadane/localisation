import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ICommande } from 'app/shared/model/commande.model';
import { CommandeService } from './commande.service';
import { IBoutique } from 'app/shared/model/boutique.model';
import { BoutiqueService } from 'app/entities/boutique';
import { IDetailRoute } from 'app/shared/model/detail-route.model';
import { DetailRouteService } from 'app/entities/detail-route';
import { IProspection } from 'app/shared/model/prospection.model';
import { ProspectionService } from 'app/entities/prospection';
import { ISecteur } from 'app/shared/model/secteur.model';
import { SecteurService } from 'app/entities/secteur';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-commande-update',
    templateUrl: './livraison-update.component.html'
})
export class LivraisonUpdateComponent implements OnInit {
    commande: ICommande;
    isSaving: boolean;

    boutiques: IBoutique[];

    detailroutes: IDetailRoute[];

    secteurs: ISecteur[];

    employees: IEmployee[];

    prospections: IProspection[];
    dateComDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected commandeService: CommandeService,
        protected boutiqueService: BoutiqueService,
        protected detailRouteService: DetailRouteService,
        protected prospectionService: ProspectionService,
        protected secteurService: SecteurService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ commande }) => {
            this.commande = commande;
        });
        this.boutiqueService
            .query({ filter: 'commande-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IBoutique[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBoutique[]>) => response.body)
            )
            .subscribe(
                (res: IBoutique[]) => {
                    if (!this.commande.boutique || !this.commande.boutique.id) {
                        this.boutiques = res;
                    } else {
                        this.boutiqueService
                            .find(this.commande.boutique.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IBoutique>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IBoutique>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IBoutique) => (this.boutiques = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.detailRouteService
            .query({ filter: 'commande-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<IDetailRoute[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDetailRoute[]>) => response.body)
            )
            .subscribe(
                (res: IDetailRoute[]) => {
                    if (!this.commande.detailRoute || !this.commande.detailRoute.id) {
                        this.detailroutes = res;
                    } else {
                        this.detailRouteService
                            .find(this.commande.detailRoute.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<IDetailRoute>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<IDetailRoute>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: IDetailRoute) => (this.detailroutes = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.secteurService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISecteur[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISecteur[]>) => response.body)
            )
            .subscribe((res: ISecteur[]) => (this.secteurs = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.commande.id !== undefined) {
            this.subscribeToSaveResponse(this.commandeService.update(this.commande));
        } else {
            this.subscribeToSaveResponse(this.commandeService.create(this.commande));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ICommande>>) {
        result.subscribe((res: HttpResponse<ICommande>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBoutiqueById(index: number, item: IBoutique) {
        return item.id;
    }

    trackDetailRouteById(index: number, item: IDetailRoute) {
        return item.id;
    }

    trackSecteurById(index: number, item: ISecteur) {
        return item.id;
    }

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }

    trackProspectionById(index: number, item: IProspection) {
        return item.id;
    }
}
