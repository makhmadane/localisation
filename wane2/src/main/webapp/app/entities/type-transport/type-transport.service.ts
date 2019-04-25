import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeTransport } from 'app/shared/model/type-transport.model';

type EntityResponseType = HttpResponse<ITypeTransport>;
type EntityArrayResponseType = HttpResponse<ITypeTransport[]>;

@Injectable({ providedIn: 'root' })
export class TypeTransportService {
    public resourceUrl = SERVER_API_URL + 'api/type-transports';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/type-transports';

    constructor(protected http: HttpClient) {}

    create(typeTransport: ITypeTransport): Observable<EntityResponseType> {
        return this.http.post<ITypeTransport>(this.resourceUrl, typeTransport, { observe: 'response' });
    }

    update(typeTransport: ITypeTransport): Observable<EntityResponseType> {
        return this.http.put<ITypeTransport>(this.resourceUrl, typeTransport, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeTransport>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeTransport[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeTransport[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
