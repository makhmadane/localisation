import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IReglement } from 'app/shared/model/reglement.model';

type EntityResponseType = HttpResponse<IReglement>;
type EntityArrayResponseType = HttpResponse<IReglement[]>;

@Injectable({ providedIn: 'root' })
export class ReglementService {
    public resourceUrl = SERVER_API_URL + 'api/reglements';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/reglements';

    constructor(protected http: HttpClient) {}

    create(reglement: IReglement): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reglement);
        return this.http
            .post<IReglement>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(reglement: IReglement): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(reglement);
        return this.http
            .put<IReglement>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IReglement>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IReglement[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IReglement[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(reglement: IReglement): IReglement {
        const copy: IReglement = Object.assign({}, reglement, {
            dateReg: reglement.dateReg != null && reglement.dateReg.isValid() ? reglement.dateReg.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateReg = res.body.dateReg != null ? moment(res.body.dateReg) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((reglement: IReglement) => {
                reglement.dateReg = reglement.dateReg != null ? moment(reglement.dateReg) : null;
            });
        }
        return res;
    }
}
