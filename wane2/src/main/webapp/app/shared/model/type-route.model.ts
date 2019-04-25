export interface ITypeRoute {
    id?: number;
    libelle?: string;
    etat?: string;
}

export class TypeRoute implements ITypeRoute {
    constructor(public id?: number, public libelle?: string, public etat?: string) {}
}
