import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IEmployee } from 'app/shared/model/employee.model';
import { EmployeeService } from './employee.service';
import { ITablette } from 'app/shared/model/tablette.model';
import { TabletteService } from 'app/entities/tablette';
import { IUser, UserService } from 'app/core';

@Component({
    selector: 'jhi-employee-update',
    templateUrl: './employee-update.component.html'
})
export class EmployeeUpdateComponent implements OnInit {
    employee: IEmployee;
    isSaving: boolean;

    tablettes: ITablette[];

    users: IUser[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected employeeService: EmployeeService,
        protected tabletteService: TabletteService,
        protected userService: UserService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ employee }) => {
            this.employee = employee;
        });
        this.tabletteService
            .query({ filter: 'employee-is-null' })
            .pipe(
                filter((mayBeOk: HttpResponse<ITablette[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITablette[]>) => response.body)
            )
            .subscribe(
                (res: ITablette[]) => {
                    if (!this.employee.tablette || !this.employee.tablette.id) {
                        this.tablettes = res;
                    } else {
                        this.tabletteService
                            .find(this.employee.tablette.id)
                            .pipe(
                                filter((subResMayBeOk: HttpResponse<ITablette>) => subResMayBeOk.ok),
                                map((subResponse: HttpResponse<ITablette>) => subResponse.body)
                            )
                            .subscribe(
                                (subRes: ITablette) => (this.tablettes = [subRes].concat(res)),
                                (subRes: HttpErrorResponse) => this.onError(subRes.message)
                            );
                    }
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
        this.userService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IUser[]>) => mayBeOk.ok),
                map((response: HttpResponse<IUser[]>) => response.body)
            )
            .subscribe((res: IUser[]) => (this.users = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.employee.id !== undefined) {
            this.subscribeToSaveResponse(this.employeeService.update(this.employee));
        } else {
            this.subscribeToSaveResponse(this.employeeService.create(this.employee));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IEmployee>>) {
        result.subscribe((res: HttpResponse<IEmployee>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTabletteById(index: number, item: ITablette) {
        return item.id;
    }

    trackUserById(index: number, item: IUser) {
        return item.id;
    }
}
