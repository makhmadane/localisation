import { Moment } from 'moment';
import { IAppro } from 'app/shared/model/appro.model';

export interface IBonLivraison {
    id?: number;
    dateBl?: Moment;
    appros?: IAppro[];
}

export class BonLivraison implements IBonLivraison {
    constructor(public id?: number, public dateBl?: Moment, public appros?: IAppro[]) {}
}
