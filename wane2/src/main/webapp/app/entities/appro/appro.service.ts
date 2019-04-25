import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IAppro } from 'app/shared/model/appro.model';

type EntityResponseType = HttpResponse<IAppro>;
type EntityArrayResponseType = HttpResponse<IAppro[]>;

@Injectable({ providedIn: 'root' })
export class ApproService {
    public resourceUrl = SERVER_API_URL + 'api/appros';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/appros';

    constructor(protected http: HttpClient) {}

    create(appro: IAppro): Observable<EntityResponseType> {
        return this.http.post<IAppro>(this.resourceUrl, appro, { observe: 'response' });
    }

    update(appro: IAppro): Observable<EntityResponseType> {
        return this.http.put<IAppro>(this.resourceUrl, appro, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IAppro>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAppro[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IAppro[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
