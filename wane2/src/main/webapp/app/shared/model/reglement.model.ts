import { Moment } from 'moment';
import { ITypeRglment } from 'app/shared/model/type-rglment.model';
import { ICommande } from 'app/shared/model/commande.model';

export interface IReglement {
    id?: number;
    dateReg?: Moment;
    montantPayer?: string;
    typeRglment?: ITypeRglment;
    commande?: ICommande;
}

export class Reglement implements IReglement {
    constructor(
        public id?: number,
        public dateReg?: Moment,
        public montantPayer?: string,
        public typeRglment?: ITypeRglment,
        public commande?: ICommande
    ) {}
}
