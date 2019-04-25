import { ISecteur } from 'app/shared/model/secteur.model';

export interface ICommune {
    id?: number;
    nomCom?: string;
    longitutde?: string;
    altitude?: string;
    etat?: string;
    secteurs?: ISecteur[];
}

export class Commune implements ICommune {
    constructor(
        public id?: number,
        public nomCom?: string,
        public longitutde?: string,
        public altitude?: string,
        public etat?: string,
        public secteurs?: ISecteur[]
    ) {}
}
