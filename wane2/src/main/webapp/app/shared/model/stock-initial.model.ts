import { IBoutique } from 'app/shared/model/boutique.model';
import { IArticle } from 'app/shared/model/article.model';

export interface IStockInitial {
    id?: number;
    qteStockInit?: string;
    qteStockEncours?: string;
    boutique?: IBoutique;
    article?: IArticle;
}

export class StockInitial implements IStockInitial {
    constructor(
        public id?: number,
        public qteStockInit?: string,
        public qteStockEncours?: string,
        public boutique?: IBoutique,
        public article?: IArticle
    ) {}
}
