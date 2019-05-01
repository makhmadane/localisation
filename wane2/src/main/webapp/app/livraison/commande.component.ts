import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ICommande } from 'app/shared/model/commande.model';
import { AccountService } from 'app/core';
import { CommandeService } from './commande.service';

@Component({
    selector: 'jhi-commande',
    templateUrl: './commande.component.html',

})
export class CommandeComponent implements OnInit, OnDestroy {
    commandes: ICommande[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;
    todo:any[];
    done:any[];

    constructor(
        protected commandeService: CommandeService,
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
            this.commandeService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ICommande[]>) => res.ok),
                    map((res: HttpResponse<ICommande[]>) => res.body)
                )
                .subscribe((res: ICommande[]) => (this.commandes = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.commandeService
            .query()
            .pipe(
                filter((res: HttpResponse<ICommande[]>) => res.ok),
                map((res: HttpResponse<ICommande[]>) => res.body)
            )
            .subscribe(
                (res: ICommande[]) => {
                    this.commandes = res;
                    this.currentSearch = '';
                    console.log(this.commandes);

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
        this.registerChangeInCommandes();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ICommande) {
        return item.id;
    }

    registerChangeInCommandes() {
        this.eventSubscriber = this.eventManager.subscribe('commandeListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
