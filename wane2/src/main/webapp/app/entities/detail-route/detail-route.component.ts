import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDetailRoute } from 'app/shared/model/detail-route.model';
import { AccountService } from 'app/core';
import { DetailRouteService } from './detail-route.service';

@Component({
    selector: 'jhi-detail-route',
    templateUrl: './detail-route.component.html'
})
export class DetailRouteComponent implements OnInit, OnDestroy {
    detailRoutes: IDetailRoute[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected detailRouteService: DetailRouteService,
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
            this.detailRouteService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IDetailRoute[]>) => res.ok),
                    map((res: HttpResponse<IDetailRoute[]>) => res.body)
                )
                .subscribe((res: IDetailRoute[]) => (this.detailRoutes = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.detailRouteService
            .query()
            .pipe(
                filter((res: HttpResponse<IDetailRoute[]>) => res.ok),
                map((res: HttpResponse<IDetailRoute[]>) => res.body)
            )
            .subscribe(
                (res: IDetailRoute[]) => {
                    this.detailRoutes = res;
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
        this.registerChangeInDetailRoutes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDetailRoute) {
        return item.id;
    }

    registerChangeInDetailRoutes() {
        this.eventSubscriber = this.eventManager.subscribe('detailRouteListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
