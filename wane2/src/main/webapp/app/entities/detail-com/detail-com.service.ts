import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IDetailCom } from 'app/shared/model/detail-com.model';

type EntityResponseType = HttpResponse<IDetailCom>;
type EntityArrayResponseType = HttpResponse<IDetailCom[]>;

@Injectable({ providedIn: 'root' })
export class DetailComService {
    public resourceUrl = SERVER_API_URL + 'api/detail-coms';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/detail-coms';

    constructor(protected http: HttpClient) {}

    create(detailCom: IDetailCom): Observable<EntityResponseType> {
        return this.http.post<IDetailCom>(this.resourceUrl, detailCom, { observe: 'response' });
    }

    update(detailCom: IDetailCom): Observable<EntityResponseType> {
        return this.http.put<IDetailCom>(this.resourceUrl, detailCom, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IDetailCom>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDetailCom[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IDetailCom[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
