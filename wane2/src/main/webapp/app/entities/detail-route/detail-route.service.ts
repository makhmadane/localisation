import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDetailRoute } from 'app/shared/model/detail-route.model';

type EntityResponseType = HttpResponse<IDetailRoute>;
type EntityArrayResponseType = HttpResponse<IDetailRoute[]>;

@Injectable({ providedIn: 'root' })
export class DetailRouteService {
    public resourceUrl = SERVER_API_URL + 'api/detail-routes';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/detail-routes';

    constructor(protected http: HttpClient) {}

    create(detailRoute: IDetailRoute): Observable<EntityResponseType> {
        return this.http.post<IDetailRoute>(this.resourceUrl, detailRoute, { observe: 'response' });
    }

    update(detailRoute: IDetailRoute): Observable<EntityResponseType> {
        return this.http.put<IDetailRoute>(this.resourceUrl, detailRoute, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDetailRoute>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDetailRoute[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDetailRoute[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
