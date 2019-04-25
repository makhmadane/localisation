import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ITypeRglment } from 'app/shared/model/type-rglment.model';

type EntityResponseType = HttpResponse<ITypeRglment>;
type EntityArrayResponseType = HttpResponse<ITypeRglment[]>;

@Injectable({ providedIn: 'root' })
export class TypeRglmentService {
    public resourceUrl = SERVER_API_URL + 'api/type-rglments';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/type-rglments';

    constructor(protected http: HttpClient) {}

    create(typeRglment: ITypeRglment): Observable<EntityResponseType> {
        return this.http.post<ITypeRglment>(this.resourceUrl, typeRglment, { observe: 'response' });
    }

    update(typeRglment: ITypeRglment): Observable<EntityResponseType> {
        return this.http.put<ITypeRglment>(this.resourceUrl, typeRglment, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<ITypeRglment>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeRglment[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<ITypeRglment[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
