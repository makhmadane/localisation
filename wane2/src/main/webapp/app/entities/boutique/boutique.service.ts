import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBoutique } from 'app/shared/model/boutique.model';

type EntityResponseType = HttpResponse<IBoutique>;
type EntityArrayResponseType = HttpResponse<IBoutique[]>;

@Injectable({ providedIn: 'root' })
export class BoutiqueService {
    public resourceUrl = SERVER_API_URL + 'api/boutiques';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/boutiques';

    constructor(protected http: HttpClient) {}

    create(boutique: IBoutique): Observable<EntityResponseType> {
        return this.http.post<IBoutique>(this.resourceUrl, boutique, { observe: 'response' });
    }

    update(boutique: IBoutique): Observable<EntityResponseType> {
        return this.http.put<IBoutique>(this.resourceUrl, boutique, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IBoutique>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBoutique[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IBoutique[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
