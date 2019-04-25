import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IRoute } from 'app/shared/model/route.model';
import { RouteService } from './route.service';
import { IMoyenTransport } from 'app/shared/model/moyen-transport.model';
import { MoyenTransportService } from 'app/entities/moyen-transport';
import { ITypeRoute } from 'app/shared/model/type-route.model';
import { TypeRouteService } from 'app/entities/type-route';
import { IUser, UserService } from 'app/core';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { ISecteur } from 'app/shared/model/secteur.model';
import { SecteurService } from 'app/entities/secteur';
import { IBoutique } from 'app/shared/model/boutique.model';
import { BoutiqueService } from 'app/entities/boutique';

@Component({
    selector: 'jhi-route-update',
    templateUrl: './route-update.component.html'
})
export class RouteUpdateComponent implements OnInit {
    route: IRoute;
    isSaving: boolean;

    moyentransports: IMoyenTransport[];

    typeroutes: ITypeRoute[];

    users: IUser[];

    employees: IEmployee[];

    secteurs: ISecteur[];

    boutiques: IBoutique[];
    dateCreationDp: any;
    datedepComDp: any;
    dateRComDp: any;
    datedepLivDp: any;
    dateRLivDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected routeService: RouteService,
        protected moyenTransportService: MoyenTransportService,
        protected typeRouteService: TypeRouteService,
        protected userService: UserService,
        protected employeeService: EmployeeService,
        protected secteurService: SecteurService,
        protected boutiqueService: BoutiqueService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ route }) => {
            this.route = route;
        });
        this.moyenTransportService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMoyenTransport[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMoyenTransport[]>) => response.body)
            )
            .subscribe((res: IMoyenTransport[]) => (this.moyentransports = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.typeRouteService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeRoute[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeRoute[]>) => response.body)
            )
            .subscribe((res: ITypeRoute[]) => (this.typeroutes = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.secteurService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ISecteur[]>) => mayBeOk.ok),
                map((response: HttpResponse<ISecteur[]>) => response.body)
            )
            .subscribe((res: ISecteur[]) => (this.secteurs = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.route.id !== undefined) {
            this.subscribeToSaveResponse(this.routeService.update(this.route));
        } else {
            this.subscribeToSaveResponse(this.routeService.create(this.route));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IRoute>>) {
        result.subscribe((res: HttpResponse<IRoute>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackMoyenTransportById(index: number, item: IMoyenTransport) {
        return item.id;
    }

    trackTypeRouteById(index: number, item: ITypeRoute) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }

    trackSecteurById(index: number, item: ISecteur) {
        return item.id;
    }

    trackBoutiqueById(index: number, item: IBoutique) {
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
