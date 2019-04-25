import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IArticle } from 'app/shared/model/article.model';
import { ArticleService } from './article.service';
import { IParfum } from 'app/shared/model/parfum.model';
import { ParfumService } from 'app/entities/parfum';
import { IDepot } from 'app/shared/model/depot.model';
import { DepotService } from 'app/entities/depot';

@Component({
    selector: 'jhi-article-update',
    templateUrl: './article-update.component.html'
})
export class ArticleUpdateComponent implements OnInit {
    article: IArticle;
    isSaving: boolean;

    parfums: IParfum[];

    depots: IDepot[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected articleService: ArticleService,
        protected parfumService: ParfumService,
        protected depotService: DepotService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ article }) => {
            this.article = article;
        });
        this.parfumService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IParfum[]>) => mayBeOk.ok),
                map((response: HttpResponse<IParfum[]>) => response.body)
            )
            .subscribe((res: IParfum[]) => (this.parfums = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.depotService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IDepot[]>) => mayBeOk.ok),
                map((response: HttpResponse<IDepot[]>) => response.body)
            )
            .subscribe((res: IDepot[]) => (this.depots = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.article.id !== undefined) {
            this.subscribeToSaveResponse(this.articleService.update(this.article));
        } else {
            this.subscribeToSaveResponse(this.articleService.create(this.article));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IArticle>>) {
        result.subscribe((res: HttpResponse<IArticle>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
    }

    protected onSaveSuccess() {
        this.isSaving = false;
        this.previousState();
    }

    protected onSaveError() {
        this.isSaving = false;
    }

    protected onError(errorMessage: string) {
        this.jhiAlertService.error(errorMessage, null, null);
    }

    trackParfumById(index: number, item: IParfum) {
        return item.id;
    }

    trackDepotById(index: number, item: IDepot) {
        return item.id;
    }
}
