import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IRoute } from 'app/shared/model/route.model';

type EntityResponseType = HttpResponse<IRoute>;
type EntityArrayResponseType = HttpResponse<IRoute[]>;

@Injectable({ providedIn: 'root' })
export class RouteService {
    public resourceUrl = SERVER_API_URL + 'api/routes';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/routes';

    constructor(protected http: HttpClient) {}

    create(route: IRoute): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(route);
        return this.http
            .post<IRoute>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(route: IRoute): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(route);
        return this.http
            .put<IRoute>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IRoute>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRoute[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IRoute[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(route: IRoute): IRoute {
        const copy: IRoute = Object.assign({}, route, {
            dateCreation: route.dateCreation != null && route.dateCreation.isValid() ? route.dateCreation.format(DATE_FORMAT) : null,
            datedepCom: route.datedepCom != null && route.datedepCom.isValid() ? route.datedepCom.format(DATE_FORMAT) : null,
            dateRCom: route.dateRCom != null && route.dateRCom.isValid() ? route.dateRCom.format(DATE_FORMAT) : null,
            datedepLiv: route.datedepLiv != null && route.datedepLiv.isValid() ? route.datedepLiv.format(DATE_FORMAT) : null,
            dateRLiv: route.dateRLiv != null && route.dateRLiv.isValid() ? route.dateRLiv.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateCreation = res.body.dateCreation != null ? moment(res.body.dateCreation) : null;
            res.body.datedepCom = res.body.datedepCom != null ? moment(res.body.datedepCom) : null;
            res.body.dateRCom = res.body.dateRCom != null ? moment(res.body.dateRCom) : null;
            res.body.datedepLiv = res.body.datedepLiv != null ? moment(res.body.datedepLiv) : null;
            res.body.dateRLiv = res.body.dateRLiv != null ? moment(res.body.dateRLiv) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((route: IRoute) => {
                route.dateCreation = route.dateCreation != null ? moment(route.dateCreation) : null;
                route.datedepCom = route.datedepCom != null ? moment(route.datedepCom) : null;
                route.dateRCom = route.dateRCom != null ? moment(route.dateRCom) : null;
                route.datedepLiv = route.datedepLiv != null ? moment(route.datedepLiv) : null;
                route.dateRLiv = route.dateRLiv != null ? moment(route.dateRLiv) : null;
            });
        }
        return res;
    }
}
