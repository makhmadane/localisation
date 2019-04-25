import { IArticle } from 'app/shared/model/article.model';
import { IBonLivraison } from 'app/shared/model/bon-livraison.model';

export interface IAppro {
    id?: number;
    qteLiv?: string;
    article?: IArticle;
    bonLivraison?: IBonLivraison;
}

export class Appro implements IAppro {
    constructor(public id?: number, public qteLiv?: string, public article?: IArticle, public bonLivraison?: IBonLivraison) {}
}
