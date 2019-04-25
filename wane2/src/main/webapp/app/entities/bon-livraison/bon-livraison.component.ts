import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBonLivraison } from 'app/shared/model/bon-livraison.model';
import { AccountService } from 'app/core';
import { BonLivraisonService } from './bon-livraison.service';

@Component({
    selector: 'jhi-bon-livraison',
    templateUrl: './bon-livraison.component.html'
})
export class BonLivraisonComponent implements OnInit, OnDestroy {
    bonLivraisons: IBonLivraison[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected bonLivraisonService: BonLivraisonService,
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
            this.bonLivraisonService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IBonLivraison[]>) => res.ok),
                    map((res: HttpResponse<IBonLivraison[]>) => res.body)
                )
                .subscribe((res: IBonLivraison[]) => (this.bonLivraisons = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.bonLivraisonService
            .query()
            .pipe(
                filter((res: HttpResponse<IBonLivraison[]>) => res.ok),
                map((res: HttpResponse<IBonLivraison[]>) => res.body)
            )
            .subscribe(
                (res: IBonLivraison[]) => {
                    this.bonLivraisons = res;
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
        this.registerChangeInBonLivraisons();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBonLivraison) {
        return item.id;
    }

    registerChangeInBonLivraisons() {
        this.eventSubscriber = this.eventManager.subscribe('bonLivraisonListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
