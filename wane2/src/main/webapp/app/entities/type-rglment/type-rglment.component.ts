import { Component, OnInit, OnDestroy } from '@angular/core';
import { HttpErrorResponse, HttpResponse } from '@angular/common/http';
import { ActivatedRoute } from '@angular/router';
import { Subscription } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiEventManager, JhiAlertService } from 'ng-jhipster';

import { ITypeRglment } from 'app/shared/model/type-rglment.model';
import { AccountService } from 'app/core';
import { TypeRglmentService } from './type-rglment.service';

@Component({
    selector: 'jhi-type-rglment',
    templateUrl: './type-rglment.component.html'
})
export class TypeRglmentComponent implements OnInit, OnDestroy {
    typeRglments: ITypeRglment[];
    currentAccount: any;
    eventSubscriber: Subscription;
    currentSearch: string;

    constructor(
        protected typeRglmentService: TypeRglmentService,
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
            this.typeRglmentService
                .search({
                    query: this.currentSearch
                })
                .pipe(
                    filter((res: HttpResponse<ITypeRglment[]>) => res.ok),
                    map((res: HttpResponse<ITypeRglment[]>) => res.body)
                )
                .subscribe((res: ITypeRglment[]) => (this.typeRglments = res), (res: HttpErrorResponse) => this.onError(res.message));
            return;
        }
        this.typeRglmentService
            .query()
            .pipe(
                filter((res: HttpResponse<ITypeRglment[]>) => res.ok),
                map((res: HttpResponse<ITypeRglment[]>) => res.body)
            )
            .subscribe(
                (res: ITypeRglment[]) => {
                    this.typeRglments = res;
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
        this.registerChangeInTypeRglments();
    }

    ngOnDestroy() {
        this.eventManager.destroy(this.eventSubscriber);
    }

    trackId(index: number, item: ITypeRglment) {
        return item.id;
    }

    registerChangeInTypeRglments() {
        this.eventSubscriber = this.eventManager.subscribe('typeRglmentListModification', response => this.loadAll());
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }
}
