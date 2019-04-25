import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IAppro } from 'app/shared/model/appro.model';
import { AccountService } from 'app/core';
import { ApproService } from './appro.service';

@Component({
    selector: 'jhi-appro',
    templateUrl: './appro.component.html'
})
export class ApproComponent implements OnInit, OnDestroy {
    appros: IAppro[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected approService: ApproService,
        protected jhiAlertService: JhiAlertService,
        protected eventManager: JhiEventManager,
        protected activatedRoute: ActivatedRoute,
        protected accountService: AccountService
    ) {
        this.currentSearch =
            this.activatedRoute.snapshot && this.activatedRoute.snapshot.params['search']
                ? this.activatedRoute.snapshot.params['search']
                : '';
    }

    loadAll() {
        if (this.currentSearch) {
            this.approService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IAppro[]>) => res.ok),
                    map((res: HttpResponse<IAppro[]>) => res.body)
                )
                .subscribe((res: IAppro[]) => (this.appros = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.approService
            .query()
            .pipe(
                filter((res: HttpResponse<IAppro[]>) => res.ok),
                map((res: HttpResponse<IAppro[]>) => res.body)
            )
            .subscribe(
                (res: IAppro[]) => {
                    this.appros = res;
                    this.currentSearch = '';
                },
                (res: HttpErrorResponse) => this.onError(res.message)
            );
    }

    search(query) {
        if (!query) {
            return this.clear();
        }
        this.currentSearch = query;
        this.loadAll();
    }

    clear() {
        this.currentSearch = '';
        this.loadAll();
    }

    ngOnInit() {
        this.loadAll();
        this.accountService.identity().then(account => {
            this.currentAccount = account;
        });
        this.registerChangeInAppros();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IAppro) {
        return item.id;
    }

    registerChangeInAppros() {
        this.eventSubscriber = this.eventManager.subscribe('approListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
