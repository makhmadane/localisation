import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IProspection } from 'app/shared/model/prospection.model';

type EntityResponseType = HttpResponse<IProspection>;
type EntityArrayResponseType = HttpResponse<IProspection[]>;

@Injectable({ providedIn: 'root' })
export class ProspectionService {
    public resourceUrl = SERVER_API_URL + 'api/prospections';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/prospections';

    constructor(protected http: HttpClient) {}

    create(prospection: IProspection): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(prospection);
        return this.http
            .post<IProspection>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(prospection: IProspection): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(prospection);
        return this.http
            .put<IProspection>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IProspection>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProspection[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IProspection[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(prospection: IProspection): IProspection {
        const copy: IProspection = Object.assign({}, prospection, {
            datedepart:
                prospection.datedepart != null && prospection.datedepart.isValid() ? prospection.datedepart.format(DATE_FORMAT) : null,
            datecreation:
                prospection.datecreation != null && prospection.datecreation.isValid() ? prospection.datecreation.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.datedepart = res.body.datedepart != null ? moment(res.body.datedepart) : null;
            res.body.datecreation = res.body.datecreation != null ? moment(res.body.datecreation) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((prospection: IProspection) => {
                prospection.datedepart = prospection.datedepart != null ? moment(prospection.datedepart) : null;
                prospection.datecreation = prospection.datecreation != null ? moment(prospection.datecreation) : null;
            });
        }
        return res;
    }
}
