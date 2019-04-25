import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMoyenTransport } from 'app/shared/model/moyen-transport.model';

type EntityResponseType = HttpResponse<IMoyenTransport>;
type EntityArrayResponseType = HttpResponse<IMoyenTransport[]>;

@Injectable({ providedIn: 'root' })
export class MoyenTransportService {
    public resourceUrl = SERVER_API_URL + 'api/moyen-transports';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/moyen-transports';

    constructor(protected http: HttpClient) {}

    create(moyenTransport: IMoyenTransport): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(moyenTransport);
        return this.http
            .post<IMoyenTransport>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(moyenTransport: IMoyenTransport): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(moyenTransport);
        return this.http
            .put<IMoyenTransport>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IMoyenTransport>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMoyenTransport[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IMoyenTransport[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(moyenTransport: IMoyenTransport): IMoyenTransport {
        const copy: IMoyenTransport = Object.assign({}, moyenTransport, {
            dateList:
                moyenTransport.dateList != null && moyenTransport.dateList.isValid() ? moyenTransport.dateList.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateList = res.body.dateList != null ? moment(res.body.dateList) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((moyenTransport: IMoyenTransport) => {
                moyenTransport.dateList = moyenTransport.dateList != null ? moment(moyenTransport.dateList) : null;
            });
        }
        return res;
    }
}
