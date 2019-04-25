import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IRoute } from 'app/shared/model/route.model';
import { AccountService } from 'app/core';
import { RouteService } from './route.service';

@Component({
    selector: 'jhi-route',
    templateUrl: './route.component.html'
})
export class RouteComponent implements OnInit, OnDestroy {
    routes: IRoute[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected routeService: RouteService,
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
            this.routeService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IRoute[]>) => res.ok),
                    map((res: HttpResponse<IRoute[]>) => res.body)
                )
                .subscribe((res: IRoute[]) => (this.routes = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.routeService
            .query()
            .pipe(
                filter((res: HttpResponse<IRoute[]>) => res.ok),
                map((res: HttpResponse<IRoute[]>) => res.body)
            )
            .subscribe(
                (res: IRoute[]) => {
                    this.routes = res;
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
        this.registerChangeInRoutes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IRoute) {
        return item.id;
    }

    registerChangeInRoutes() {
        this.eventSubscriber = this.eventManager.subscribe('routeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
