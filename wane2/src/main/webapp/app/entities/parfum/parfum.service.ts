import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IParfum } from 'app/shared/model/parfum.model';

type EntityResponseType = HttpResponse<IParfum>;
type EntityArrayResponseType = HttpResponse<IParfum[]>;

@Injectable({ providedIn: 'root' })
export class ParfumService {
    public resourceUrl = SERVER_API_URL + 'api/parfums';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/parfums';

    constructor(protected http: HttpClient) {}

    create(parfum: IParfum): Observable<EntityResponseType> {
        return this.http.post<IParfum>(this.resourceUrl, parfum, { observe: 'response' });
    }

    update(parfum: IParfum): Observable<EntityResponseType> {
        return this.http.put<IParfum>(this.resourceUrl, parfum, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IParfum>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IParfum[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IParfum[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
