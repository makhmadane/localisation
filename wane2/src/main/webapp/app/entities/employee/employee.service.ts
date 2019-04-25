import { Injectable } from '@angular/core';
import { HttpClient, HttpResponse } from '@angular/common/http';
import { Observable } from 'rxjs';

import { SERVER_API_URL } from 'app/app.constants';
import { createRequestOption } from 'app/shared';
import { IEmployee } from 'app/shared/model/employee.model';

type EntityResponseType = HttpResponse<IEmployee>;
type EntityArrayResponseType = HttpResponse<IEmployee[]>;

@Injectable({ providedIn: 'root' })
export class EmployeeService {
    public resourceUrl = SERVER_API_URL + 'api/employees';
    public resourceSearchUrl = SERVER_API_URL + 'api/_search/employees';

    constructor(protected http: HttpClient) {}

    create(employee: IEmployee): Observable<EntityResponseType> {
        return this.http.post<IEmployee>(this.resourceUrl, employee, { observe: 'response' });
    }

    update(employee: IEmployee): Observable<EntityResponseType> {
        return this.http.put<IEmployee>(this.resourceUrl, employee, { observe: 'response' });
    }

    find(id: number): Observable<EntityResponseType> {
        return this.http.get<IEmployee>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    query(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEmployee[]>(this.resourceUrl, { params: options, observe: 'response' });
    }

    delete(id: number): Observable<HttpResponse<any>> {
        return this.http.delete<any>(`${this.resourceUrl}/${id}`, { observe: 'response' });
    }

    search(req?: any): Observable<EntityArrayResponseType> {
        const options = createRequestOption(req);
        return this.http.get<IEmployee[]>(this.resourceSearchUrl, { params: options, observe: 'response' });
    }
}
