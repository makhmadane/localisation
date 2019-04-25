import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IProspection } from 'app/shared/model/prospection.model';
import { ProspectionService } from './prospection.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';
import { IMoyenTransport } from 'app/shared/model/moyen-transport.model';
import { MoyenTransportService } from 'app/entities/moyen-transport';
import { ICommande } from 'app/shared/model/commande.model';
import { CommandeService } from 'app/entities/commande';

@Component({
    selector: 'jhi-prospection-update',
    templateUrl: './prospection-update.component.html'
})
export class ProspectionUpdateComponent implements OnInit {
    prospection: IProspection;
    isSaving: boolean;

    employees: IEmployee[];

    moyentransports: IMoyenTransport[];

    commandes: ICommande[];
    datedepartDp: any;
    datecreationDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected prospectionService: ProspectionService,
        protected employeeService: EmployeeService,
        protected moyenTransportService: MoyenTransportService,
        protected commandeService: CommandeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ prospection }) => {
            this.prospection = prospection;
        });
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.moyenTransportService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IMoyenTransport[]>) => mayBeOk.ok),
                map((response: HttpResponse<IMoyenTransport[]>) => response.body)
            )
            .subscribe((res: IMoyenTransport[]) => (this.moyentransports = res), (res: HttpErrorResponse) => this.onError(res.message));
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
        if (this.prospection.id !== undefined) {
            this.subscribeToSaveResponse(this.prospectionService.update(this.prospection));
        } else {
            this.subscribeToSaveResponse(this.prospectionService.create(this.prospection));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IProspection>>) {
        result.subscribe((res: HttpResponse<IProspection>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackEmployeeById(index: number, item: IEmployee) {
        return item.id;
    }

    trackMoyenTransportById(index: number, item: IMoyenTransport) {
        return item.id;
    }

    trackCommandeById(index: number, item: ICommande) {
        return item.id;
    }
}
