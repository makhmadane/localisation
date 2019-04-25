import { IBoutique } from 'app/shared/model/boutique.model';

export interface IMetier {
    id?: number;
    libelle?: string;
    etatmetier?: string;
    boutiques?: IBoutique[];
}

export class Metier implements IMetier {
    constructor(public id?: number, public libelle?: string, public etatmetier?: string, public boutiques?: IBoutique[]) {}
}
