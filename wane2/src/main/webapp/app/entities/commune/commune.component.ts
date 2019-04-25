import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICommune } from 'app/shared/model/commune.model';
import { AccountService } from 'app/core';
import { CommuneService } from './commune.service';

@Component({
    selector: 'jhi-commune',
    templateUrl: './commune.component.html'
})
export class CommuneComponent implements OnInit, OnDestroy {
    communes: ICommune[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected communeService: CommuneService,
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
            this.communeService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ICommune[]>) => res.ok),
                    map((res: HttpResponse<ICommune[]>) => res.body)
                )
                .subscribe((res: ICommune[]) => (this.communes = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.communeService
            .query()
            .pipe(
                filter((res: HttpResponse<ICommune[]>) => res.ok),
                map((res: HttpResponse<ICommune[]>) => res.body)
            )
            .subscribe(
                (res: ICommune[]) => {
                    this.communes = res;
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
        this.registerChangeInCommunes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICommune) {
        return item.id;
    }

    registerChangeInCommunes() {
        this.eventSubscriber = this.eventManager.subscribe('communeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
