import { IMoyenTransport } from 'app/shared/model/moyen-transport.model';

export interface ITypeTransport {
    id?: number;
    libelle?: string;
    moyenTransports?: IMoyenTransport[];
}

export class TypeTransport implements ITypeTransport {
    constructor(public id?: number, public libelle?: string, public moyenTransports?: IMoyenTransport[]) {}
}
