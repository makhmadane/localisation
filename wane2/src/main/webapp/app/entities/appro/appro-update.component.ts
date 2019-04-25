import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IAppro } from 'app/shared/model/appro.model';
import { ApproService } from './appro.service';
import { IArticle } from 'app/shared/model/article.model';
import { ArticleService } from 'app/entities/article';
import { IBonLivraison } from 'app/shared/model/bon-livraison.model';
import { BonLivraisonService } from 'app/entities/bon-livraison';

@Component({
    selector: 'jhi-appro-update',
    templateUrl: './appro-update.component.html'
})
export class ApproUpdateComponent implements OnInit {
    appro: IAppro;
    isSaving: boolean;

    articles: IArticle[];

    bonlivraisons: IBonLivraison[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected approService: ApproService,
        protected articleService: ArticleService,
        protected bonLivraisonService: BonLivraisonService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ appro }) => {
            this.appro = appro;
        });
        this.articleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IArticle[]>) => mayBeOk.ok),
                map((response: HttpResponse<IArticle[]>) => response.body)
            )
            .subscribe((res: IArticle[]) => (this.articles = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.bonLivraisonService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IBonLivraison[]>) => mayBeOk.ok),
                map((response: HttpResponse<IBonLivraison[]>) => response.body)
            )
            .subscribe((res: IBonLivraison[]) => (this.bonlivraisons = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.appro.id !== undefined) {
            this.subscribeToSaveResponse(this.approService.update(this.appro));
        } else {
            this.subscribeToSaveResponse(this.approService.create(this.appro));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IAppro>>) {
        result.subscribe((res: HttpResponse<IAppro>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackArticleById(index: number, item: IArticle) {
        return item.id;
    }

    trackBonLivraisonById(index: number, item: IBonLivraison) {
        return item.id;
    }
}
