import { IRoute } from 'app/shared/model/route.model';
import { ICommune } from 'app/shared/model/commune.model';
import { IBoutique } from 'app/shared/model/boutique.model';
import { ICommande } from 'app/shared/model/commande.model';

export interface ISecteur {
    id?: number;
    nomSecteur?: string;
    longitutde?: string;
    altitude?: string;
    etat?: string;
    routes?: IRoute[];
    communes?: ICommune[];
    boutiques?: IBoutique[];
    commandes?: ICommande[];
}

export class Secteur implements ISecteur {
    constructor(
        public id?: number,
        public nomSecteur?: string,
        public longitutde?: string,
        public altitude?: string,
        public etat?: string,
        public routes?: IRoute[],
        public communes?: ICommune[],
        public boutiques?: IBoutique[],
        public commandes?: ICommande[]
    ) {}
}
