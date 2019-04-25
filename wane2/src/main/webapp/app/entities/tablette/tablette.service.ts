import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITablette } from 'app/shared/model/tablette.model';

type EntityResponseType = HttpResponse<ITablette>;
type EntityArrayResponseType = HttpResponse<ITablette[]>;

@Injectable({ providedIn: 'root' })
export class TabletteService {
    public resourceUrl = SERVER_API_URL + 'api/tablettes';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/tablettes';

    constructor(protected http: HttpClient) {}

    create(tablette: ITablette): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(tablette);
        return this.http
            .post<ITablette>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(tablette: ITablette): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(tablette);
        return this.http
            .put<ITablette>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ITablette>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITablette[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ITablette[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(tablette: ITablette): ITablette {
        const copy: ITablette = Object.assign({}, tablette, {
            dateServ: tablette.dateServ != null && tablette.dateServ.isValid() ? tablette.dateServ.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateServ = res.body.dateServ != null ? moment(res.body.dateServ) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((tablette: ITablette) => {
                tablette.dateServ = tablette.dateServ != null ? moment(tablette.dateServ) : null;
            });
        }
        return res;
    }
}
