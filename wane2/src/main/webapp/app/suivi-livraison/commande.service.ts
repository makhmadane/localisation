import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';
import * as moment from 'moment';
import { DATE_FORMAT } from 'app/shared/constants/input.constants';
import { map } from 'rxjs/operators';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { ICommande } from 'app/shared/model/commande.model';
import {IArticle} from "../shared/model/article.model";

type EntityResponseType = HttpResponse<ICommande>;
type EntityArrayResponseType = HttpResponse<ICommande[]>;

@Injectable({ providedIn: 'root' })
export class CommandeService {
    public resourceUrl = SERVER_API_URL + 'api/commandes';
    public resourceArticle = SERVER_API_URL + 'api/commandeArticle';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/commandes';
    public resourceAllEtat = SERVER_API_URL + 'api/commandesEtat';
    public resourceAllBoutique = SERVER_API_URL + 'api/commandesBoutique';
    public resourceAllSecteur = SERVER_API_URL + 'api/commandesSecteur';
    public resourceAllDate = SERVER_API_URL + 'api/commandesDate';
    public resourceAllPrevendeur = SERVER_API_URL + 'api/commandesPrevendeur';
    public resourceChangeEtat = SERVER_API_URL + 'api/changeEtat';

    constructor(protected http: HttpClient) {}

    create(commande: ICommande): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commande);
        return this.http
            .post<ICommande>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
    findAllEtat(){
        return this.http
            .get<any>(`${this.resourceAllEtat}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findAllBoutique(){
        return this.http
            .get<any>(`${this.resourceAllBoutique}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findAllSecteur(){
        return this.http
            .get<any>(`${this.resourceAllSecteur}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
    findAllDate(){
        return this.http
            .get<any>(`${this.resourceAllDate}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    findAllPrevendeur(){
        return this.http
            .get<any>(`${this.resourceAllPrevendeur}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }


    update(commande: ICommande): Observable<EntityResponseType> {
        const copy = this.convertDateFromClient(commande);
        return this.http
            .put<ICommande>(this.resourceUrl, copy, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
    changeEtat(name,id): Observable<EntityResponseType> {
        return this.http
            .get<any>(`${this.resourceChangeEtat}/${id}/${name}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http
            .get<ICommande>(`${this.resourceUrl}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }
    public findarticle(id: number): Observable<EntityResponseType> {
        return this.http
            .get<any>(`${this.resourceArticle}/${id}`, { observe: 'response' })
            .pipe(map((res: EntityResponseType) => this.convertDateFromServer(res)));
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommande[]>(this.resourceUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http
            .get<ICommande[]>(this.resourceSearchUrl, { params: options, observe: 'response' })
            .pipe(map((res: EntityArrayResponseType) => this.convertDateArrayFromServer(res)));
    }

    protected convertDateFromClient(commande: ICommande): ICommande {
        const copy: ICommande = Object.assign({}, commande, {
            dateCom: commande.dateCom != null && commande.dateCom.isValid() ? commande.dateCom.format(DATE_FORMAT) : null
        });
        return copy;
    }

    protected convertDateFromServer(res: EntityResponseType): EntityResponseType {
        if (res.body) {
            res.body.dateCom = res.body.dateCom != null ? moment(res.body.dateCom) : null;
        }
        return res;
    }

    protected convertDateArrayFromServer(res: EntityArrayResponseType): EntityArrayResponseType {
        if (res.body) {
            res.body.forEach((commande: ICommande) => {
                commande.dateCom = commande.dateCom != null ? moment(commande.dateCom) : null;
            });
        }
        return res;
    }
}
