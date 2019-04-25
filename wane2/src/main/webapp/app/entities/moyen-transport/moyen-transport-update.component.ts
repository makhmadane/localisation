import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import * as moment from 'moment';
import { JhiAlertService } from 'ng-jhipster';
import { IMoyenTransport } from 'app/shared/model/moyen-transport.model';
import { MoyenTransportService } from './moyen-transport.service';
import { ITypeTransport } from 'app/shared/model/type-transport.model';
import { TypeTransportService } from 'app/entities/type-transport';

@Component({
    selector: 'jhi-moyen-transport-update',
    templateUrl: './moyen-transport-update.component.html'
})
export class MoyenTransportUpdateComponent implements OnInit {
    moyenTransport: IMoyenTransport;
    isSaving: boolean;

    typetransports: ITypeTransport[];
    dateListDp: any;

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected moyenTransportService: MoyenTransportService,
        protected typeTransportService: TypeTransportService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ moyenTransport }) => {
            this.moyenTransport = moyenTransport;
        });
        this.typeTransportService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ITypeTransport[]>) => mayBeOk.ok),
                map((response: HttpResponse<ITypeTransport[]>) => response.body)
            )
            .subscribe((res: ITypeTransport[]) => (this.typetransports = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.moyenTransport.id !== undefined) {
            this.subscribeToSaveResponse(this.moyenTransportService.update(this.moyenTransport));
        } else {
            this.subscribeToSaveResponse(this.moyenTransportService.create(this.moyenTransport));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IMoyenTransport>>) {
        result.subscribe((res: HttpResponse<IMoyenTransport>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackTypeTransportById(index: number, item: ITypeTransport) {
        return item.id;
    }
}
