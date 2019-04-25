import { Moment } from 'moment';
import { IDetailRoute } from 'app/shared/model/detail-route.model';
import { IMoyenTransport } from 'app/shared/model/moyen-transport.model';
import { ITypeRoute } from 'app/shared/model/type-route.model';
import { IUser } from 'app/core/user/user.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { ISecteur } from 'app/shared/model/secteur.model';
import { IBoutique } from 'app/shared/model/boutique.model';

export interface IRoute {
    id?: number;
    numero?: string;
    journee?: string;
    dateCreation?: Moment;
    datedepCom?: Moment;
    dateRCom?: Moment;
    heureDepCom?: string;
    heureFinCom?: string;
    heureDepLiv?: string;
    heureFinLiv?: string;
    journeeLiv?: string;
    datedepLiv?: Moment;
    dateRLiv?: Moment;
    etat?: string;
    detailRoutes?: IDetailRoute[];
    moyenTransport?: IMoyenTransport;
    commande?: ITypeRoute;
    livraison?: ITypeRoute;
    gerantCommande?: IUser;
    prevendeur?: IEmployee;
    gerantlivraison?: IEmployee;
    livreur?: IEmployee;
    secteurs?: ISecteur[];
    boutiques?: IBoutique[];
}

export class Route implements IRoute {
    constructor(
        public id?: number,
        public numero?: string,
        public journee?: string,
        public dateCreation?: Moment,
        public datedepCom?: Moment,
        public dateRCom?: Moment,
        public heureDepCom?: string,
        public heureFinCom?: string,
        public heureDepLiv?: string,
        public heureFinLiv?: string,
        public journeeLiv?: string,
        public datedepLiv?: Moment,
        public dateRLiv?: Moment,
        public etat?: string,
        public detailRoutes?: IDetailRoute[],
        public moyenTransport?: IMoyenTransport,
        public commande?: ITypeRoute,
        public livraison?: ITypeRoute,
        public gerantCommande?: IUser,
        public prevendeur?: IEmployee,
        public gerantlivraison?: IEmployee,
        public livreur?: IEmployee,
        public secteurs?: ISecteur[],
        public boutiques?: IBoutique[]
    ) {}
}
