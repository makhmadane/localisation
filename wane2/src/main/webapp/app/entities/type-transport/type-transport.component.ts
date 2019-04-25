import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITypeTransport } from 'app/shared/model/type-transport.model';
import { AccountService } from 'app/core';
import { TypeTransportService } from './type-transport.service';

@Component({
    selector: 'jhi-type-transport',
    templateUrl: './type-transport.component.html'
})
export class TypeTransportComponent implements OnInit, OnDestroy {
    typeTransports: ITypeTransport[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected typeTransportService: TypeTransportService,
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
            this.typeTransportService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ITypeTransport[]>) => res.ok),
                    map((res: HttpResponse<ITypeTransport[]>) => res.body)
                )
                .subscribe((res: ITypeTransport[]) => (this.typeTransports = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.typeTransportService
            .query()
            .pipe(
                filter((res: HttpResponse<ITypeTransport[]>) => res.ok),
                map((res: HttpResponse<ITypeTransport[]>) => res.body)
            )
            .subscribe(
                (res: ITypeTransport[]) => {
                    this.typeTransports = res;
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
        this.registerChangeInTypeTransports();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITypeTransport) {
        return item.id;
    }

    registerChangeInTypeTransports() {
        this.eventSubscriber = this.eventManager.subscribe('typeTransportListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
