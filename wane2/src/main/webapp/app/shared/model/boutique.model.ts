import { IStockInitial } from 'app/shared/model/stock-initial.model';
import { IDetailRoute } from 'app/shared/model/detail-route.model';
import { IMetier } from 'app/shared/model/metier.model';
import { IQualite } from 'app/shared/model/qualite.model';
import { ISecteur } from 'app/shared/model/secteur.model';
import { IRoute } from 'app/shared/model/route.model';
import { ICommande } from 'app/shared/model/commande.model';
import { IProspection } from 'app/shared/model/prospection.model';

export const enum Civilite {
    M = 'M',
    Mme = 'Mme'
}

export interface IBoutique {
    id?: number;
    nomBoutique?: string;
    prenom?: string;
    civilite?: Civilite;
    nom?: string;
    telephone?: string;
    telproprietaire?: string;
    longitude?: string;
    altitude?: string;
    etat?: string;
    periodicite?: string;
    dateDernierCom?: string;
    dateDernierLiv?: string;
    solde?: string;
    commandeEnCours?: string;
    stockInitials?: IStockInitial[];
    detailRoutes?: IDetailRoute[];
    metier?: IMetier;
    qualite?: IQualite;
    secteur?: ISecteur;
    routes?: IRoute[];
    commande?: ICommande;
    prospection?: IProspection;
}

export class Boutique implements IBoutique {
    constructor(
        public id?: number,
        public nomBoutique?: string,
        public prenom?: string,
        public civilite?: Civilite,
        public nom?: string,
        public telephone?: string,
        public telproprietaire?: string,
        public longitude?: string,
        public altitude?: string,
        public etat?: string,
        public periodicite?: string,
        public dateDernierCom?: string,
        public dateDernierLiv?: string,
        public solde?: string,
        public commandeEnCours?: string,
        public stockInitials?: IStockInitial[],
        public detailRoutes?: IDetailRoute[],
        public metier?: IMetier,
        public qualite?: IQualite,
        public secteur?: ISecteur,
        public routes?: IRoute[],
        public commande?: ICommande,
        public prospection?: IProspection
    ) {}
}
