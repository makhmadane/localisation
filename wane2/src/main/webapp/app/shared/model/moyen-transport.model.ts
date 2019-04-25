import { Moment } from 'moment';
import { ITypeTransport } from 'app/shared/model/type-transport.model';
import { IRoute } from 'app/shared/model/route.model';
import { IProspection } from 'app/shared/model/prospection.model';

export interface IMoyenTransport {
    id?: number;
    matricule?: string;
    dateList?: Moment;
    etat?: string;
    vitesse?: string;
    capacite?: string;
    typeTransport?: ITypeTransport;
    routes?: IRoute[];
    prospections?: IProspection[];
}

export class MoyenTransport implements IMoyenTransport {
    constructor(
        public id?: number,
        public matricule?: string,
        public dateList?: Moment,
        public etat?: string,
        public vitesse?: string,
        public capacite?: string,
        public typeTransport?: ITypeTransport,
        public routes?: IRoute[],
        public prospections?: IProspection[]
    ) {}
}
