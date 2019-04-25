import { Injectable } from '@angular/core';
import { HttpResponse } from '@angular/common/http';
import { Resolve, ActivatedRouteSnapshot, RouterStateSnapshot, Routes } from '@angular/router';
import { UserRouteAccessService } from 'app/core';
import { Observable, of } from 'rxjs';
import { filter, map } from 'rxjs/operators';
import { Parfum } from 'app/shared/model/parfum.model';
import { ParfumService } from './parfum.service';
import { ParfumComponent } from './parfum.component';
import { ParfumDetailComponent } from './parfum-detail.component';
import { ParfumUpdateComponent } from './parfum-update.component';
import { ParfumDeletePopupComponent } from './parfum-delete-dialog.component';
import { IParfum } from 'app/shared/model/parfum.model';

@Injectable({ providedIn: 'root' })
export class ParfumResolve implements Resolve<IParfum> {
    constructor(private service: ParfumService) {}

    resolve(route: ActivatedRouteSnapshot, state: RouterStateSnapshot): Observable<IParfum> {
        const id = route.params['id'] ? route.params['id'] : null;
        if (id) {
            return this.service.find(id).pipe(
                filter((response: HttpResponse<Parfum>) => response.ok),
                map((parfum: HttpResponse<Parfum>) => parfum.body)
            );
        }
        return of(new Parfum());
    }
}

export const parfumRoute: Routes = [
    {
        path: '',
        component: ParfumComponent,
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.parfum.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/view',
        component: ParfumDetailComponent,
        resolve: {
            parfum: ParfumResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.parfum.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: 'new',
        component: ParfumUpdateComponent,
        resolve: {
            parfum: ParfumResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.parfum.home.title'
        },
        canActivate: [UserRouteAccessService]
    },
    {
        path: ':id/edit',
        component: ParfumUpdateComponent,
        resolve: {
            parfum: ParfumResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.parfum.home.title'
        },
        canActivate: [UserRouteAccessService]
    }
];

export const parfumPopupRoute: Routes = [
    {
        path: ':id/delete',
        component: ParfumDeletePopupComponent,
        resolve: {
            parfum: ParfumResolve
        },
        data: {
            authorities: ['ROLE_USER'],
            pageTitle: 'yesColaApp.parfum.home.title'
        },
        canActivate: [UserRouteAccessService],
        outlet: 'popup'
    }
];
