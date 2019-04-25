import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITablette } from 'app/shared/model/tablette.model';
import { AccountService } from 'app/core';
import { TabletteService } from './tablette.service';

@Component({
    selector: 'jhi-tablette',
    templateUrl: './tablette.component.html'
})
export class TabletteComponent implements OnInit, OnDestroy {
    tablettes: ITablette[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected tabletteService: TabletteService,
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
            this.tabletteService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ITablette[]>) => res.ok),
                    map((res: HttpResponse<ITablette[]>) => res.body)
                )
                .subscribe((res: ITablette[]) => (this.tablettes = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.tabletteService
            .query()
            .pipe(
                filter((res: HttpResponse<ITablette[]>) => res.ok),
                map((res: HttpResponse<ITablette[]>) => res.body)
            )
            .subscribe(
                (res: ITablette[]) => {
                    this.tablettes = res;
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
        this.registerChangeInTablettes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITablette) {
        return item.id;
    }

    registerChangeInTablettes() {
        this.eventSubscriber = this.eventManager.subscribe('tabletteListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
