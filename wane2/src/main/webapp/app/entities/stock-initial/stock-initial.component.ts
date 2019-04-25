import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IStockInitial } from 'app/shared/model/stock-initial.model';
import { AccountService } from 'app/core';
import { StockInitialService } from './stock-initial.service';

@Component({
    selector: 'jhi-stock-initial',
    templateUrl: './stock-initial.component.html'
})
export class StockInitialComponent implements OnInit, OnDestroy {
    stockInitials: IStockInitial[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected stockInitialService: StockInitialService,
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
            this.stockInitialService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IStockInitial[]>) => res.ok),
                    map((res: HttpResponse<IStockInitial[]>) => res.body)
                )
                .subscribe((res: IStockInitial[]) => (this.stockInitials = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.stockInitialService
            .query()
            .pipe(
                filter((res: HttpResponse<IStockInitial[]>) => res.ok),
                map((res: HttpResponse<IStockInitial[]>) => res.body)
            )
            .subscribe(
                (res: IStockInitial[]) => {
                    this.stockInitials = res;
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
        this.registerChangeInStockInitials();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IStockInitial) {
        return item.id;
    }

    registerChangeInStockInitials() {
        this.eventSubscriber = this.eventManager.subscribe('stockInitialListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
