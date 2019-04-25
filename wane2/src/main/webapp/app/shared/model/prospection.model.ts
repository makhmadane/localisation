import { Moment } from 'moment';
import { IBoutique } from 'app/shared/model/boutique.model';
import { IEmployee } from 'app/shared/model/employee.model';
import { IMoyenTransport } from 'app/shared/model/moyen-transport.model';
import { ICommande } from 'app/shared/model/commande.model';

export interface IProspection {
    id?: number;
    datedepart?: Moment;
    datecreation?: Moment;
    heuredepart?: string;
    heurearrive?: string;
    journee?: string;
    nbreatteint?: string;
    nbrprevue?: string;
    etat?: string;
    boutiques?: IBoutique[];
    prevendeur?: IEmployee;
    gerant?: IEmployee;
    moyenTransport?: IMoyenTransport;
    commande?: ICommande;
    commandes?: ICommande[];
}

export class Prospection implements IProspection {
    constructor(
        public id?: number,
        public datedepart?: Moment,
        public datecreation?: Moment,
        public heuredepart?: string,
        public heurearrive?: string,
        public journee?: string,
        public nbreatteint?: string,
        public nbrprevue?: string,
        public etat?: string,
        public boutiques?: IBoutique[],
        public prevendeur?: IEmployee,
        public gerant?: IEmployee,
        public moyenTransport?: IMoyenTransport,
        public commande?: ICommande,
        public commandes?: ICommande[]
    ) {}
}
