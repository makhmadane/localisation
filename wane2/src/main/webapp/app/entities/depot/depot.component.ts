import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDepot } from 'app/shared/model/depot.model';
import { AccountService } from 'app/core';
import { DepotService } from './depot.service';

@Component({
    selector: 'jhi-depot',
    templateUrl: './depot.component.html'
})
export class DepotComponent implements OnInit, OnDestroy {
    depots: IDepot[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected depotService: DepotService,
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
            this.depotService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IDepot[]>) => res.ok),
                    map((res: HttpResponse<IDepot[]>) => res.body)
                )
                .subscribe((res: IDepot[]) => (this.depots = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.depotService
            .query()
            .pipe(
                filter((res: HttpResponse<IDepot[]>) => res.ok),
                map((res: HttpResponse<IDepot[]>) => res.body)
            )
            .subscribe(
                (res: IDepot[]) => {
                    this.depots = res;
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
        this.registerChangeInDepots();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDepot) {
        return item.id;
    }

    registerChangeInDepots() {
        this.eventSubscriber = this.eventManager.subscribe('depotListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
