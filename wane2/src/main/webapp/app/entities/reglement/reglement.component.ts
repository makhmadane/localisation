import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IReglement } from 'app/shared/model/reglement.model';
import { AccountService } from 'app/core';
import { ReglementService } from './reglement.service';

@Component({
    selector: 'jhi-reglement',
    templateUrl: './reglement.component.html'
})
export class ReglementComponent implements OnInit, OnDestroy {
    reglements: IReglement[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected reglementService: ReglementService,
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
            this.reglementService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IReglement[]>) => res.ok),
                    map((res: HttpResponse<IReglement[]>) => res.body)
                )
                .subscribe((res: IReglement[]) => (this.reglements = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.reglementService
            .query()
            .pipe(
                filter((res: HttpResponse<IReglement[]>) => res.ok),
                map((res: HttpResponse<IReglement[]>) => res.body)
            )
            .subscribe(
                (res: IReglement[]) => {
                    this.reglements = res;
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
        this.registerChangeInReglements();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IReglement) {
        return item.id;
    }

    registerChangeInReglements() {
        this.eventSubscriber = this.eventManager.subscribe('reglementListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
