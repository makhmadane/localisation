import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { ITablette } from 'app/shared/model/tablette.model';
import { TabletteService } from './tablette.service';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from 'app/entities/employee';

@Component({
    selector: 'jhi-tablette-update',
    templateUrl: './tablette-update.component.html'
})
export class TabletteUpdateComponent implements OnInit {
    tablette: ITablette;
    isSaving: boolean;

    employees: IEmployee[];
    dateServDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected tabletteService: TabletteService,
        protected employeeService: EmployeeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ tablette }) => {
            this.tablette = tablette;
        });
        this.employeeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IEmployee[]>) => mayBeOk.ok),
                map((response: HttpResponse<IEmployee[]>) => response.body)
            )
            .subscribe((res: IEmployee[]) => (this.employees = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.tablette.id !== undefined) {
            this.subscribeToSaveResponse(this.tabletteService.update(this.tablette));
        } else {
            this.subscribeToSaveResponse(this.tabletteService.create(this.tablette));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<ITablette>>) {
        result.subscribe((res: HttpResponse<ITablette>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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
}
