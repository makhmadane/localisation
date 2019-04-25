import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IMetier } from 'app/shared/model/metier.model';
import { AccountService } from 'app/core';
import { MetierService } from './metier.service';

@Component({
    selector: 'jhi-metier',
    templateUrl: './metier.component.html'
})
export class MetierComponent implements OnInit, OnDestroy {
    metiers: IMetier[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected metierService: MetierService,
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
            this.metierService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IMetier[]>) => res.ok),
                    map((res: HttpResponse<IMetier[]>) => res.body)
                )
                .subscribe((res: IMetier[]) => (this.metiers = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.metierService
            .query()
            .pipe(
                filter((res: HttpResponse<IMetier[]>) => res.ok),
                map((res: HttpResponse<IMetier[]>) => res.body)
            )
            .subscribe(
                (res: IMetier[]) => {
                    this.metiers = res;
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
        this.registerChangeInMetiers();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IMetier) {
        return item.id;
    }

    registerChangeInMetiers() {
        this.eventSubscriber = this.eventManager.subscribe('metierListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
