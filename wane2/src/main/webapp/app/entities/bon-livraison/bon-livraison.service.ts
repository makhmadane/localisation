import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IBonLivraison } from 'app/shared/model/bon-livraison.model';

type EntityResponseType = HttpResponse<IBonLivraison>;
type EntityArrayResponseType = HttpResponse<IBonLivraison[]>;

@Injectable({ providedIn: 'root' })
export class BonLivraisonService {
    public resourceUrl = SERVER_API_URL + 'api/bon-livraisons';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/bon-livraisons';

    constructor(protected http: HttpClient) {}

    create(bonLivraison: IBonLivraison): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bonLivraison);
        return this.http
            .post<IBonLivraison>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    update(bonLivraison: IBonLivraison): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(bonLivraison);
        return this.http
            .put<IBonLivraison>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<IBonLivraison>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBonLivraison[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<IBonLivraison[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(bonLivraison: IBonLivraison): IBonLivraison {
        const copy: IBonLivraison = Object.assign({}, bonLivraison, {
            dateBl: bonLivraison.dateBl != null && bonLivraison.dateBl.isValid() ? bonLivraison.dateBl.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateBl = res.body.dateBl != null ? moment(res.body.dateBl) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((bonLivraison: IBonLivraison) => {
                bonLivraison.dateBl = bonLivraison.dateBl != null ? moment(bonLivraison.dateBl) : null;
            });
        }
        return res;
    }
}
