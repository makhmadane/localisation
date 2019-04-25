import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IParfum } from 'app/shared/model/parfum.model';
import { AccountService } from 'app/core';
import { ParfumService } from './parfum.service';

@Component({
    selector: 'jhi-parfum',
    templateUrl: './parfum.component.html'
})
export class ParfumComponent implements OnInit, OnDestroy {
    parfums: IParfum[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected parfumService: ParfumService,
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
            this.parfumService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IParfum[]>) => res.ok),
                    map((res: HttpResponse<IParfum[]>) => res.body)
                )
                .subscribe((res: IParfum[]) => (this.parfums = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.parfumService
            .query()
            .pipe(
                filter((res: HttpResponse<IParfum[]>) => res.ok),
                map((res: HttpResponse<IParfum[]>) => res.body)
            )
            .subscribe(
                (res: IParfum[]) => {
                    this.parfums = res;
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
        this.registerChangeInParfums();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IParfum) {
        return item.id;
    }

    registerChangeInParfums() {
        this.eventSubscriber = this.eventManager.subscribe('parfumListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
