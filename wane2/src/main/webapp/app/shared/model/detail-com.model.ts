import { IArticle } from 'app/shared/model/article.model';
import { ICommande } from 'app/shared/model/commande.model';

export interface IDetailCom {
    id?: number;
    qteCom?: string;
    qteLiv?: string;
    article?: IArticle;
    commande?: ICommande;
}

export class DetailCom implements IDetailCom {
    constructor(
        public id?: number,
        public qteCom?: string,
        public qteLiv?: string,
        public article?: IArticle,
        public commande?: ICommande
    ) {}
}
