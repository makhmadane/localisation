import { Moment } from 'moment';
import { IBoutique } from 'app/shared/model/boutique.model';
import { IDetailRoute } from 'app/shared/model/detail-route.model';
import { IDetailCom } from 'app/shared/model/detail-com.model';
import { IProspection } from 'app/shared/model/prospection.model';
import { IReglement } from 'app/shared/model/reglement.model';
import { ISecteur } from 'app/shared/model/secteur.model';
import { IEmployee } from 'app/shared/model/employee.model';

export interface ICommande {
    id?: number;
    numCom?: string;
    dateCom?: Moment;
    etat?: string;
    montantLiv?: string;
    montantRest?: string;
    boutique?: IBoutique;
    detailRoute?: IDetailRoute;
    detailComs?: IDetailCom[];
    prospections?: IProspection[];
    reglements?: IReglement[];
    secteur?: ISecteur;
    livreur?: IEmployee;
    prevendeur?: IEmployee;
    prospection?: IProspection;
}

export class Commande implements ICommande {
    constructor(
        public id?: number,
        public numCom?: string,
        public dateCom?: Moment,
        public etat?: string,
        public montantLiv?: string,
        public montantRest?: string,
        public boutique?: IBoutique,
        public detailRoute?: IDetailRoute,
        public detailComs?: IDetailCom[],
        public prospections?: IProspection[],
        public reglements?: IReglement[],
        public secteur?: ISecteur,
        public livreur?: IEmployee,
        public prevendeur?: IEmployee,
        public prospection?: IProspection
    ) {}
}
