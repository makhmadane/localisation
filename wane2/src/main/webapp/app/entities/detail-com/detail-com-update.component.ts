import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { HttpResponse, HttpErrorResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { JhiAlertService } from 'ng-jhipster';
import { IDetailCom } from 'app/shared/model/detail-com.model';
import { DetailComService } from './detail-com.service';
import { IArticle } from 'app/shared/model/article.model';
import { ArticleService } from 'app/entities/article';
import { ICommande } from 'app/shared/model/commande.model';
import { CommandeService } from 'app/entities/commande';

@Component({
    selector: 'jhi-detail-com-update',
    templateUrl: './detail-com-update.component.html'
})
export class DetailComUpdateComponent implements OnInit {
    detailCom: IDetailCom;
    isSaving: boolean;

    articles: IArticle[];

    commandes: ICommande[];

    constructor(
        protected jhiAlertService: JhiAlertService,
        protected detailComService: DetailComService,
        protected articleService: ArticleService,
        protected commandeService: CommandeService,
        protected activatedRoute: ActivatedRoute
    ) {}

    ngOnInit() {
        this.isSaving = false;
        this.activatedRoute.data.subscribe(({ detailCom }) => {
            this.detailCom = detailCom;
        });
        this.articleService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<IArticle[]>) => mayBeOk.ok),
                map((response: HttpResponse<IArticle[]>) => response.body)
            )
            .subscribe((res: IArticle[]) => (this.articles = res), (res: HttpErrorResponse) => this.onError(res.message));
        this.commandeService
            .query()
            .pipe(
                filter((mayBeOk: HttpResponse<ICommande[]>) => mayBeOk.ok),
                map((response: HttpResponse<ICommande[]>) => response.body)
            )
            .subscribe((res: ICommande[]) => (this.commandes = res), (res: HttpErrorResponse) => this.onError(res.message));
    }

    previousState() {
        window.history.back();
    }

    save() {
        this.isSaving = true;
        if (this.detailCom.id !== undefined) {
            this.subscribeToSaveResponse(this.detailComService.update(this.detailCom));
        } else {
            this.subscribeToSaveResponse(this.detailComService.create(this.detailCom));
        }
    }

    protected subscribeToSaveResponse(result: Observable<HttpResponse<IDetailCom>>) {
        result.subscribe((res: HttpResponse<IDetailCom>) => this.onSaveSuccess(), (res: HttpErrorResponse) => this.onSaveError());
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

    trackCommandeById(index: number, item: ICommande) {
        return item.id;
    }
}
