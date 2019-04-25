import { IArticle } from 'app/shared/model/article.model';

export interface IParfum {
    id?: number;
    numeroparf?: string;
    libelle?: string;
    etat?: string;
    articles?: IArticle[];
}

export class Parfum implements IParfum {
    constructor(
        public id?: number,
        public numeroparf?: string,
        public libelle?: string,
        public etat?: string,
        public articles?: IArticle[]
    ) {}
}
