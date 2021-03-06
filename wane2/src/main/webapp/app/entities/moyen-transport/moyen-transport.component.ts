import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMoyenTransport } from 'app/shared/model/moyen-transport.model';
import { AccountService } from 'app/core';
import { MoyenTransportService } from './moyen-transport.service';

@Component({
    selector: 'jhi-moyen-transport',
    templateUrl: './moyen-transport.component.html'
})
export class MoyenTransportComponent implements OnInit, OnDestroy {
    moyenTransports: IMoyenTransport[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected moyenTransportService: MoyenTransportService,
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
            this.moyenTransportService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IMoyenTransport[]>) => res.ok),
                    map((res: HttpResponse<IMoyenTransport[]>) => res.body)
                )
                .subscribe((res: IMoyenTransport[]) => (this.moyenTransports = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.moyenTransportService
            .query()
            .pipe(
                filter((res: HttpResponse<IMoyenTransport[]>) => res.ok),
                map((res: HttpResponse<IMoyenTransport[]>) => res.body)
            )
            .subscribe(
                (res: IMoyenTransport[]) => {
                    this.moyenTransports = res;
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
        this.registerChangeInMoyenTransports();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMoyenTransport) {
        return item.id;
    }

    registerChangeInMoyenTransports() {
        this.eventSubscriber = this.eventManager.subscribe('moyenTransportListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
