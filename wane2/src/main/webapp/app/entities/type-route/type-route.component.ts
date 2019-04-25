import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITypeRoute } from 'app/shared/model/type-route.model';
import { AccountService } from 'app/core';
import { TypeRouteService } from './type-route.service';

@Component({
    selector: 'jhi-type-route',
    templateUrl: './type-route.component.html'
})
export class TypeRouteComponent implements OnInit, OnDestroy {
    typeRoutes: ITypeRoute[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected typeRouteService: TypeRouteService,
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
            this.typeRouteService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ITypeRoute[]>) => res.ok),
                    map((res: HttpResponse<ITypeRoute[]>) => res.body)
                )
                .subscribe((res: ITypeRoute[]) => (this.typeRoutes = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.typeRouteService
            .query()
            .pipe(
                filter((res: HttpResponse<ITypeRoute[]>) => res.ok),
                map((res: HttpResponse<ITypeRoute[]>) => res.body)
            )
            .subscribe(
                (res: ITypeRoute[]) => {
                    this.typeRoutes = res;
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
        this.registerChangeInTypeRoutes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITypeRoute) {
        return item.id;
    }

    registerChangeInTypeRoutes() {
        this.eventSubscriber = this.eventManager.subscribe('typeRouteListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
