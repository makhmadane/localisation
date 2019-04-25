import { ITablette } from 'app/shared/model/tablette.model';
import { IUser } from 'app/core/user/user.model';

export interface IEmployee {
    id?: number;
    nomcomplet?: string;
    etat?: string;
    telephone?: string;
    adresse?: string;
    tablette?: ITablette;
    user?: IUser;
}

export class Employee implements IEmployee {
    constructor(
        public id?: number,
        public nomcomplet?: string,
        public etat?: string,
        public telephone?: string,
        public adresse?: string,
        public tablette?: ITablette,
        public user?: IUser
    ) {}
}
