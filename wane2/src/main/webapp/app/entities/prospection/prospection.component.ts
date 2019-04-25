import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { IProspection } from 'app/shared/model/prospection.model';
import { AccountService } from 'app/core';
import { ProspectionService } from './prospection.service';
import {MatCardModule} from '@angular/material/card';
@Component({
    selector: 'jhi-prospection',
    templateUrl: './prospection.component.html'
})
export class ProspectionComponent implements OnInit, OnDestroy {
    prospections: IProspection[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected prospectionService: ProspectionService,
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
    loadTablette(){
        console.log("dane")
    }

    loadAll() {
        if (this.currentSearch) {
            this.prospectionService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<IProspection[]>) => res.ok),
                    map((res: HttpResponse<IProspection[]>) => res.body)
                )
                .subscribe((res: IProspection[]) => (
                    this.prospections = res



                ), (res: HttpErrorResponse) => this.onError(res.message));

            return;
        }
        this.prospectionService
            .query()
            .pipe(
                filter((res: HttpResponse<IProspection[]>) => res.ok),
                map((res: HttpResponse<IProspection[]>) => res.body)
            )
            .subscribe(
                (res: IProspection[]) => {
                    this.prospections = res;
                    this.currentSearch = '';
                    console.log(this.prospections)
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
        this.registerChangeInProspections();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: IProspection) {
        return item.id;
    }

    registerChangeInProspections() {
        this.eventSubscriber = this.eventManager.subscribe('prospectionListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
