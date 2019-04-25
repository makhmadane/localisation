import { Moment } from 'moment';
import { IEmployee } from 'app/shared/model/employee.model';

export interface ITablette {
    id?: number;
    numero?: string;
    dateServ?: Moment;
    etat?: string;
    numeropuce?: string;
    employee?: IEmployee;
}

export class Tablette implements ITablette {
    constructor(
        public id?: number,
        public numero?: string,
        public dateServ?: Moment,
        public etat?: string,
        public numeropuce?: string,
        public employee?: IEmployee
    ) {}
}
