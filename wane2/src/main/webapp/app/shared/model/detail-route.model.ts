import { ICommande } from 'app/shared/model/commande.model';
import { IRoute } from 'app/shared/model/route.model';
import { IBoutique } from 'app/shared/model/boutique.model';

export interface IDetailRoute {
    id?: number;
    heureACom?: string;
    heureFCom?: string;
    heureALiv?: string;
    heureFLiv?: string;
    distanceDepot?: string;
    commande?: ICommande;
    route?: IRoute;
    boutique?: IBoutique;
}

export class DetailRoute implements IDetailRoute {
    constructor(
        public id?: number,
        public heureACom?: string,
        public heureFCom?: string,
        public heureALiv?: string,
        public heureFLiv?: string,
        public distanceDepot?: string,
        public commande?: ICommande,
        public route?: IRoute,
        public boutique?: IBoutique
    ) {}
}
