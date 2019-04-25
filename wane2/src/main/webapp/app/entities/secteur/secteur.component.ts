import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ISecteur } from 'app/shared/model/secteur.model';
import { AccountService } from 'app/core';
import { SecteurService } from './secteur.service';

@Component({
    selector: 'jhi-secteur',
    templateUrl: './secteur.component.html'
})
export class SecteurComponent implements OnInit, OnDestroy {
    secteurs: ISecteur[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected secteurService: SecteurService,
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
            this.secteurService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ISecteur[]>) => res.ok),
                    map((res: HttpResponse<ISecteur[]>) => res.body)
                )
                .subscribe((res: ISecteur[]) => (this.secteurs = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.secteurService
            .query()
            .pipe(
                filter((res: HttpResponse<ISecteur[]>) => res.ok),
                map((res: HttpResponse<ISecteur[]>) => res.body)
            )
            .subscribe(
                (res: ISecteur[]) => {
                    this.secteurs = res;
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
        this.registerChangeInSecteurs();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ISecteur) {
        return item.id;
    }

    registerChangeInSecteurs() {
        this.eventSubscriber = this.eventManager.subscribe('secteurListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
