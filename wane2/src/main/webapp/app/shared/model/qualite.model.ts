import { IBoutique } from 'app/shared/model/boutique.model';

export interface IQualite {
    id?: number;
    libelle?: string;
    etat?: string;
    boutiques?: IBoutique[];
}

export class Qualite implements IQualite {
    constructor(public id?: number, public libelle?: string, public etat?: string, public boutiques?: IBoutique[]) {}
}
