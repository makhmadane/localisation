import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IStockInitial } from 'app/shared/model/stock-initial.model';
import { StockInitialService } from './stock-initial.service';
import { IBoutique } from 'app/shared/model/boutique.model';
import { BoutiqueService } from 'app/entities/boutique';
import { IArticle } from 'app/shared/model/article.model';
import { ArticleService } from 'app/entities/article';

@Component({
    selector: 'jhi-stock-initial-update',
    templateUrl: './stock-initial-update.component.html'
})
export class StockInitialUpdateComponent implements OnInit {
    stockInitial: IStockInitial;
    isSaving: boolean;

    boutiques: IBoutique[];

    articles: IArticle[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected stockInitialService: StockInitialService,
        protected boutiqueService: BoutiqueService,
        protected articleService: ArticleService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ stockInitial }) => {
            this.stockInitial = stockInitial;
        });
        this.boutiqueService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBoutique[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBoutique[]>) => response.body)
            )
            .subscribe((res: IBoutique[]) => (this.boutiques = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.articleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IArticle[]>) => mayBeOk.ok),
                map((response: HttpResponse<IArticle[]>) => response.body)
            )
            .subscribe((res: IArticle[]) => (this.articles = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.stockInitial.id !== undefined) {
            this.subscribeToSaveResponse(this.stockInitialService.update(this.stockInitial));
        } else {
            this.subscribeToSaveResponse(this.stockInitialService.create(this.stockInitial));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IStockInitial>>) {
        result.subscribe((res: HttpResponse<IStockInitial>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackBoutiqueById(index: number, item: IBoutique) {
        return item.id;
    }

    trackArticleById(index: number, item: IArticle) {
        return item.id;
    }
}
