import { IArticle } from 'app/shared/model/article.model';

export interface IDepot {
    id?: number;
    adresse?: string;
    telephone?: string;
    email?: string;
    longitude?: string;
    altitude?: string;
    etat?: string;
    articles?: IArticle[];
}

export class Depot implements IDepot {
    constructor(
        public id?: number,
        public adresse?: string,
        public telephone?: string,
        public email?: string,
        public longitude?: string,
        public altitude?: string,
        public etat?: string,
        public articles?: IArticle[]
    ) {}
}
