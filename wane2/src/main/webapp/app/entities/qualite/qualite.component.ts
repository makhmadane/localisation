import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IQualite } from 'app/shared/model/qualite.model';
import { AccountService } from 'app/core';
import { QualiteService } from './qualite.service';

@Component({
    selector: 'jhi-qualite',
    templateUrl: './qualite.component.html'
})
export class QualiteComponent implements OnInit, OnDestroy {
    qualites: IQualite[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected qualiteService: QualiteService,
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
            this.qualiteService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IQualite[]>) => res.ok),
                    map((res: HttpResponse<IQualite[]>) => res.body)
                )
                .subscribe((res: IQualite[]) => (this.qualites = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.qualiteService
            .query()
            .pipe(
                filter((res: HttpResponse<IQualite[]>) => res.ok),
                map((res: HttpResponse<IQualite[]>) => res.body)
            )
            .subscribe(
                (res: IQualite[]) => {
                    this.qualites = res;
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
        this.registerChangeInQualites();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IQualite) {
        return item.id;
    }

    registerChangeInQualites() {
        this.eventSubscriber = this.eventManager.subscribe('qualiteListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
