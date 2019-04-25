import { IReglement } from 'app/shared/model/reglement.model';

export interface ITypeRglment {
    id?: number;
    libelle?: string;
    etat?: string;
    reglements?: IReglement[];
}

export class TypeRglment implements ITypeRglment {
    constructor(public id?: number, public libelle?: string, public etat?: string, public reglements?: IReglement[]) {}
}
