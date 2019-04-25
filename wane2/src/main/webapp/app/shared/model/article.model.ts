import { IStockInitial } from 'app/shared/model/stock-initial.model';
import { IDetailCom } from 'app/shared/model/detail-com.model';
import { IAppro } from 'app/shared/model/appro.model';
import { IParfum } from 'app/shared/model/parfum.model';
import { IDepot } from 'app/shared/model/depot.model';

export interface IArticle {
    id?: number;
    nomarticle?: string;
    numeroarticle?: string;
    qteStock?: string;
    qteSeuil?: string;
    pack?: string;
    etat?: string;
    prix?: string;
    stockInitials?: IStockInitial[];
    detailComs?: IDetailCom[];
    appros?: IAppro[];
    parfum?: IParfum;
    depot?: IDepot;
}

export class Article implements IArticle {
    constructor(
        public id?: number,
        public nomarticle?: string,
        public numeroarticle?: string,
        public qteStock?: string,
        public qteSeuil?: string,
        public pack?: string,
        public etat?: string,
        public prix?: string,
        public stockInitials?: IStockInitial[],
        public detailComs?: IDetailCom[],
        public appros?: IAppro[],
        public parfum?: IParfum,
        public depot?: IDepot
    ) {}
}
