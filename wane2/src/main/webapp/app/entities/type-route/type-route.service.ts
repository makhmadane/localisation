import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeRoute } from 'app/shared/model/type-route.model';

type EntityResponseType = HttpResponse<ITypeRoute>;
type EntityArrayResponseType = HttpResponse<ITypeRoute[]>;

@Injectable({ providedIn: 'root' })
export class TypeRouteService {
    public resourceUrl = SERVER_API_URL + 'api/type-routes';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/type-routes';

    constructor(protected http: HttpClient) {}

    create(typeRoute: ITypeRoute): Observable<EntityResponseType> {
        return this.http.post<ITypeRoute>(this.resourceUrl, typeRoute, { observe: 'response' });
    }

    update(typeRoute: ITypeRoute): Observable<EntityResponseType> {
        return this.http.put<ITypeRoute>(this.resourceUrl, typeRoute, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeRoute>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeRoute[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeRoute[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
