import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IMetier } from 'app/shared/model/metier.model';

type EntityResponseType = HttpResponse<IMetier>;
type EntityArrayResponseType = HttpResponse<IMetier[]>;

@Injectable({ providedIn: 'root' })
export class MetierService {
    public resourceUrl = SERVER_API_URL + 'api/metiers';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/metiers';

    constructor(protected http: HttpClient) {}

    create(metier: IMetier): Observable<EntityResponseType> {
        return this.http.post<IMetier>(this.resourceUrl, metier, { observe: 'response' });
    }

    update(metier: IMetier): Observable<EntityResponseType> {
        return this.http.put<IMetier>(this.resourceUrl, metier, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IMetier>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMetier[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IMetier[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
