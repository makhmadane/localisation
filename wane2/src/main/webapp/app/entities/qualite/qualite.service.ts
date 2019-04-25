import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IQualite } from 'app/shared/model/qualite.model';

type EntityResponseType = HttpResponse<IQualite>;
type EntityArrayResponseType = HttpResponse<IQualite[]>;

@Injectable({ providedIn: 'root' })
export class QualiteService {
    public resourceUrl = SERVER_API_URL + 'api/qualites';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/qualites';

    constructor(protected http: HttpClient) {}

    create(qualite: IQualite): Observable<EntityResponseType> {
        return this.http.post<IQualite>(this.resourceUrl, qualite, { observe: 'response' });
    }

    update(qualite: IQualite): Observable<EntityResponseType> {
        return this.http.put<IQualite>(this.resourceUrl, qualite, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IQualite>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IQualite[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IQualite[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
