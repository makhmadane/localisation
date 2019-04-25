import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IBoutique } from 'app/shared/model/boutique.model';
import { AccountService } from 'app/core';
import { BoutiqueService } from './boutique.service';

@Component({
    selector: 'jhi-boutique',
    templateUrl: './boutique.component.html'
})
export class BoutiqueComponent implements OnInit, OnDestroy {
    boutiques: IBoutique[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected boutiqueService: BoutiqueService,
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
            this.boutiqueService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IBoutique[]>) => res.ok),
                    map((res: HttpResponse<IBoutique[]>) => res.body)
                )
                .subscribe((res: IBoutique[]) => (this.boutiques = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.boutiqueService
            .query()
            .pipe(
                filter((res: HttpResponse<IBoutique[]>) => res.ok),
                map((res: HttpResponse<IBoutique[]>) => res.body)
            )
            .subscribe(
                (res: IBoutique[]) => {
                    this.boutiques = res;
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
        this.registerChangeInBoutiques();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IBoutique) {
        return item.id;
    }

    registerChangeInBoutiques() {
        this.eventSubscriber = this.eventManager.subscribe('boutiqueListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
