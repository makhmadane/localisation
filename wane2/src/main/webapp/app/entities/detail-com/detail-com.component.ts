import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IDetailCom } from 'app/shared/model/detail-com.model';
import { AccountService } from 'app/core';
import { DetailComService } from './detail-com.service';

@Component({
    selector: 'jhi-detail-com',
    templateUrl: './detail-com.component.html'
})
export class DetailComComponent implements OnInit, OnDestroy {
    detailComs: IDetailCom[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected detailComService: DetailComService,
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
            this.detailComService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IDetailCom[]>) => res.ok),
                    map((res: HttpResponse<IDetailCom[]>) => res.body)
                )
                .subscribe((res: IDetailCom[]) => (this.detailComs = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.detailComService
            .query()
            .pipe(
                filter((res: HttpResponse<IDetailCom[]>) => res.ok),
                map((res: HttpResponse<IDetailCom[]>) => res.body)
            )
            .subscribe(
                (res: IDetailCom[]) => {
                    this.detailComs = res;
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
        this.registerChangeInDetailComs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IDetailCom) {
        return item.id;
    }

    registerChangeInDetailComs() {
        this.eventSubscriber = this.eventManager.subscribe('detailComListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
