import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IStockInitial } from 'app/shared/model/stock-initial.model';

type EntityResponseType = HttpResponse<IStockInitial>;
type EntityArrayResponseType = HttpResponse<IStockInitial[]>;

@Injectable({ providedIn: 'root' })
export class StockInitialService {
    public resourceUrl = SERVER_API_URL + 'api/stock-initials';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/stock-initials';

    constructor(protected http: HttpClient) {}

    create(stockInitial: IStockInitial): Observable<EntityResponseType> {
        return this.http.post<IStockInitial>(this.resourceUrl, stockInitial, { observe: 'response' });
    }

    update(stockInitial: IStockInitial): Observable<EntityResponseType> {
        return this.http.put<IStockInitial>(this.resourceUrl, stockInitial, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IStockInitial>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStockInitial[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IStockInitial[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
